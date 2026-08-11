# AI Solution Validator

Verifică dacă o soluție implementată satisface cerințele documentate — cu **dovadă de
execuție** (coverage per test, mutation testing), nu cu opinia unui model.

CLI interactiv Spring Boot. Pornește, întreabă de unde ia codul, rulează, livrează raport
în terminal **și** un frontend HTML de sine stătător.

---

## Flux

```
$ java -jar asv.jar

  ? Sursa codului      > clonează un repo
                         folder local
  ? URL                  https://git.bank.ro/payments.git
  ? Document cerințe     ./cerinte.pdf
  ? Rulări / criteriu    3

  [1/4] intake ........ 18 cerințe → 43 criterii  (7 ambigue)
  [2/4] index ......... coverage per test: 214 teste indexate
  [3/4] investigate ... 43 criterii × 3 rulări  ████████░░ 78%
  [4/4] raport ........ gate=FAIL · acord mediu 0.91

  → out/report.md
  → out/report.html     ← livrabilul pentru oameni
```

---

## Structura

```
src/main/java/com/bank/asv/
│
├── AsvApplication.java
│
├── cli/                     Interacțiunea cu utilizatorul
│   ├── ValidationWizard.java     întrebările de la pornire (Spring Shell ComponentFlow)
│   ├── ProgressView.java         bara de progres pe cele 4 faze
│   └── ConsoleReporter.java      raportul în terminal
│
├── source/                  De unde vine codul
│   ├── RepoSource.java           interfață: resolve() → Path
│   ├── GitCloneSource.java       clonează cu JGit într-un temp dir
│   └── LocalFolderSource.java    validează un folder existent
│
├── model/                   CONTRACTUL — records. Se scrie primul, apoi se îngheață.
│   ├── AcceptanceCriterion.java  criteriu atomic + verificabilitate
│   ├── Finding.java              verdict + evidence + searchLog + absenceArgument
│   ├── Verdict.java              IMPLEMENTED | PARTIAL | MISSING | UNVERIFIABLE
│   └── Matrix.java               matricea agregată + release gate
│
├── engine/                  EXECUȚIA — interfețe + adaptoare. Aici e valoarea durabilă.
│   ├── SourceSearch.java         → RipgrepSearch, JavaParserSearch
│   ├── CoverageIndex.java        → JacocoCoverageIndex   (per test, o singură rulare)
│   ├── MutationRunner.java       → PitMutationRunner
│   └── TestRunner.java           → JUnitPlatformRunner
│
├── tools/                   @McpTool — ce vede agentul. FĂRĂ logică, doar traduc spre engine/.
│   ├── SearchTools.java          code_search, find_symbol, find_callers, find_endpoints
│   ├── CoverageTools.java        tests_covering_lines   ← unealta centrală
│   ├── MutationTools.java        mutation_test
│   └── RepoTools.java            repo_map, read_file, git_log
│
├── agent/                   Invocarea modelului
│   ├── GeminiRunner.java         interfață → CliGeminiRunner, MockGeminiRunner
│   └── PromptLoader.java         încarcă protocoalele versionate
│
├── pipeline/                Orchestrarea — subțire intenționat, zero euristici
│   ├── IntakeService.java        PDF → criterii atomice + glosar business→cod
│   ├── InvestigationService.java 1 criteriu = 1 sesiune × k rulări (virtual threads)
│   └── CalibrationService.java   acord între rulări → escaladare la om
│
└── report/
    ├── MarkdownReport.java
    └── HtmlReport.java           un singur .html, fără server, fără npm

src/main/resources/
├── prompts/intake.v1.md          protocoale versionate — se schimbă fără recompilare
├── prompts/investigate.v1.md
└── templates/report.html         șablonul frontend-ului

src/test/java/com/bank/asv/goldenset/    defecte injectate + precision/recall
```

---

## Arhitectura

### Principiul

> Nu bagi LLM-ul într-un pipeline scris de tine. Îi dai un **mediu de unelte** și un
> **contract de ieșire**.

Test pentru orice componentă nouă: *se depreciază când apare următorul model?*
Retrieval semantic, chunking, euristici de potrivire → da, nu le construi.
Unelte de execuție, contract, calibrare → nu, alea sunt produsul.

### Cele patru straturi

**1. Intake** — documentul de cerințe → criterii atomice cu ID stabil, fiecare clasificat
`verifiable` / `ambiguous` / `unverifiable`, plus un glosar business→cod
(`"ordin de plată programat"` → `["StandingOrder", "recurringPayment"]`).
Baseline-ul se îngheață și se semnează. Raportul „7 din 43 criterii sunt ambigue, iată care”
e valoare livrată **înainte** de a atinge codul.

**2. Unelte (MCP)** — expunem strict ce un model nu poate face singur:

| Unealtă | Răspunde la |
|---|---|
| `code_search` (ripgrep) | „chiar nu există nicăieri?” — recall garantat, nu similaritate |
| `tests_covering_lines` (JaCoCo) | „care teste execută liniile astea?” — din execuție reală |
| `mutation_test` (PIT) | „testul e real sau doar coverage teatru?” |

**3. Investigație** — o sesiune de agent per `(criteriu × seed)`, context proaspăt.
Codebase-ul nu se încarcă niciodată; fiecare sesiune atinge câteva fișiere, la cerere.

**4. Calibrare** — k rulări → `agreement`. Dezacord ⇒ `DISPUTED` ⇒ escaladare la om.
Nu e un bug, e output-ul corect când judecătorul nu e stabil.

### Unitatea de lucru: criteriul, nu repo-ul

De aici rezultă că mărimea codebase-ului e irelevantă arhitectural. Costul e
`O(număr de criterii)`. Mărimea repo-ului afectează latența unui `code_search`, atât.

### Contractul, impus de tipuri și schemă — nu de rugăminți în prompt

```
verdict = MISSING                 ⇒ absenceArgument obligatoriu (min. 3 strategii de căutare)
verdict ∈ {IMPLEMENTED, PARTIAL}  ⇒ implementationEvidence nevid (file + lines)
verdict = IMPLEMENTED             ⇒ testEvidence nevid
```

**Asimetria sarcinii probei** e intenționată: prezența se dovedește cu o citare, absența cu
un argument. Într-un repo mare riscul dominant nu e halucinația — e ca agentul să caute
prost și să declare `MISSING`. Un `MISSING` greșit trimite un om să implementeze ceva ce există.

### Poartă, nu scor

`release_gate` e `PASS` / `FAIL` / `PENDING_REVIEW` cu motive enumerabile. Deliberat **nu**
există un `78/100`: nu e derivabil, nu e contestabil, și e primul lucru care distruge
credibilitatea în fața unui auditor. „3 criterii fără implementare” se poate apăra.

---

## Rulare

```bash
mvn spring-boot:run          # interactiv
java -jar asv.jar --repo ./payments --doc cerinte.pdf --runs 3    # neinteractiv, pentru CI
java -jar asv.jar --mock     # fără Gemini instalat, pe fixture — pentru dezvoltare
```

Repo-ul țintă are nevoie de JaCoCo și PIT în `pom.xml`.

---

## Ieșiri

| Fișier | Pentru cine |
|---|---|
| terminal | tine, în timp ce rulează |
| `out/report.md` | pull request, ticket |
| `out/report.html` | **livrabilul** — un singur fișier, se deschide în browser, se trimite pe mail |
| `out/matrix.json` | mașini: CI gate, integrări |
| `out/findings/*.json` | trail de audit — cele k rulări brute, imutabile |

Frontend-ul nu e un dump de tabel. Explică: lanțul de dovezi per criteriu
(citat din PDF → `file:line` → testul care execută liniile → scor de mutație), ce înseamnă
fiecare verdict, de ce `DISPUTED` e o funcție și nu o eroare, și ce anume blochează release-ul.

---

## Împărțirea muncii

Un singur punct de sincronizare: **`model/` se scrie în ziua 1, împreună, apoi se îngheață.**
După asta, cele patru fire nu mai ating aceleași fișiere.

| Fir | Pachete | Depinde de | Livrabil |
|---|---|---|---|
| **A — Execuție** | `engine/` coverage+mutation, `tools/Coverage*`, `tools/Mutation*` | nimic | „ce teste ating liniile 88–104” pe repo real |
| **B — Navigare** | `engine/SourceSearch`, `tools/Search*`, `tools/Repo*` | nimic | ripgrep + JavaParser, simboluri exacte |
| **C — Cerințe** | `pipeline/Intake`, `agent/`, `resources/prompts/` | nimic | `requirements.json` + raport de calitate |
| **D — Livrare** | `cli/`, `source/`, `pipeline/Calibration`, `report/`, `goldenset/` | doar `model/` | wizard, matrice, HTML, precision/recall |

**Ce face paralelizarea să funcționeze:** D lucrează contra lui `MockGeminiRunner` din ziua 1.
Altfel D așteaptă două săptămâni. Fiecare interfață din `engine/` are un mock în `src/test/`.

**De ce ajută Spring aici:** fiecare unealtă e un `@Component` descoperit automat — nu există
fișier-registru comun, deci A și B adaugă unelte în paralel fără conflicte de merge.

Două integrări reale: **ziua 5** — A și B se conectează la serverul MCP; **ziua 8** — cerințele
reale ale lui C înlocuiesc fixture-ul, mock-ul lui D e schimbat cu Gemini real.

Mai puțini oameni: la 3 → A, B+C, D. La 2 → A+B, C+D.
A e cel mai riscant fir — dă-l celui mai bun om și cere rezultat până în ziua 3.
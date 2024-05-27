package com.example.auction_app.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String AUDIT_FILE = "audit.csv";
    private static AuditService instance;
    private PrintWriter writer;

    private AuditService() {
        try {
            writer = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        writer.println(methodName + "," + timestamp);
        writer.flush();
    }
}

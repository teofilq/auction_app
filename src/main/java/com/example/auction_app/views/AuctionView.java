package com.example.auction_app.views;

import com.example.auction_app.models.Auction;
import com.example.auction_app.models.Product;
import com.example.auction_app.models.User;
import com.example.auction_app.services.AuctionService;
import com.example.auction_app.services.ProductService;

import java.sql.SQLException;
import java.util.*;

public class AuctionView {
    private final AuctionService auctionService;
    private final ProductService productService;
    private final Scanner scanner;
    private final User loggedInUser;

    public AuctionView(Scanner scanner, User loggedInUser) {
        this.auctionService = new AuctionService();
        this.productService = new ProductService();
        this.scanner = scanner;
        this.loggedInUser = loggedInUser;
    }

    public void showAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctions();
        if (auctions.isEmpty()) {
            System.out.println("No auctions available.");
        } else {

            TreeSet<Auction> sortedAuctions = new TreeSet<>(Comparator.comparing(Auction::getStartDate));
            sortedAuctions.addAll(auctions);

            System.out.println("Available auctions:");

            for (Auction auction : sortedAuctions) {
                Product product = productService.getProduct(auction.getProductID());
                if (product != null) {
                    System.out.println("Product Name: " + product.getName());
                }
                System.out.println("Start Price: " + auction.getStartPrice());
                System.out.println("Min Step: " + auction.getMinStep());
                System.out.println("Start Date: " + auction.getStartDate());
                System.out.println("End Date: " + auction.getEndDate());
                System.out.println("Status: " + auction.getStatus());
                if (auction.getWinnerID() != null) {
                    System.out.println("Winner ID: " + auction.getWinnerID());
                }
                System.out.println("-----------------------------------");
            }
        }
    }

    public void startAuction() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available to start an auction.");
            return;
        }

        Map<Integer, Product> productMap = new HashMap<>();
        System.out.println("Available products:");
        int index = 1;
        for (Product product : products) {
            productMap.put(index, product);
            System.out.println(index + ". " + product.getName() + " (ID: " + product.getProductID() + ")");
            index++;
        }

        System.out.print("Select the product number to start an auction: ");
        int productNumber = scanner.nextInt();
        scanner.nextLine();

        Product selectedProduct = productMap.get(productNumber);
        if (selectedProduct == null) {
            System.out.println("Invalid product selection.");
            return;
        }

        System.out.print("Enter Start Price: ");
        double startPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Min Step: ");
        double minStep = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter number of days for the auction to last: ");
        int days = scanner.nextInt();
        scanner.nextLine();

        Date endDate = new Date(System.currentTimeMillis() + (days * 24L * 60 * 60 * 1000));

        Auction auction = new Auction(0, selectedProduct.getProductID(), startPrice, minStep, new Date(), endDate, "Active", null);
        try {
            auctionService.addAuction(auction);
            System.out.println("Auction started successfully for product: " + selectedProduct.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to start auction due to database error.");
        }
    }
}

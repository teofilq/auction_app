package com.example.auction_app.views;

import com.example.auction_app.models.User;
import com.example.auction_app.services.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserView {
    private final UserService userService;
    private final Scanner scanner;
    private User loggedInUser;

    public UserView(Scanner scanner) {
        this.userService = new UserService();
        this.scanner = scanner;
    }

    public void registerUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        User user = new User(0, email, password, name, address, phone, new java.util.Date());

        try {
            userService.addUser(user);
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'Users.Email'")) {
                System.out.println("User with email " + email + " already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void loginUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            System.out.println("Login successful. Welcome, " + user.getName() + "!");
            userMenu();
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    private void userMenu() {
        boolean running = true;

        while (running) {
            System.out.println("=========================================");
            System.out.println("=              USER MENU                =");
            System.out.println("=========================================");
            System.out.println("=              Auction Options          =");
            System.out.println("-----------------------------------------");
            System.out.println("= 1. See All Auctions                   =");
            System.out.println("= 2. Start an Auction                   =");
            System.out.println("-----------------------------------------");
            System.out.println("=              Product Options          =");
            System.out.println("-----------------------------------------");
            System.out.println("= 3. Add a Product                      =");
            System.out.println("= 4. Delete a Product                   =");
            System.out.println("= 5. Update a Product                   =");
            System.out.println("-----------------------------------------");
            System.out.println("= 6. Logout                             =");
            System.out.println("=========================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    AuctionView auctionView = new AuctionView(scanner, loggedInUser);
                    auctionView.showAllAuctions();
                    break;
                case 2:
                    AuctionView auctionViewStart = new AuctionView(scanner, loggedInUser);
                    auctionViewStart.startAuction();
                    break;
                case 3:
                    ProductView productView = new ProductView(scanner, loggedInUser);
                    productView.addProduct();
                    break;
                case 4:
                    ProductView productViewDelete = new ProductView(scanner, loggedInUser);
                    productViewDelete.deleteProduct();
                    break;
                case 5:
                    ProductView productViewUpdate = new ProductView(scanner, loggedInUser);
                    productViewUpdate.updateProduct();
                    break;
                case 6:
                    running = false;
                    loggedInUser = null;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

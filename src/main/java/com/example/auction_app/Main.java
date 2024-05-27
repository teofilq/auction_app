package com.example.auction_app;

import com.example.auction_app.views.UserView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserView userView = new UserView(scanner);

        boolean running = true;

        while (running) {
            System.out.println("=========================================");
            System.out.println("=           WELCOME TO AUCTION APP      =");
            System.out.println("=                                       =");
            System.out.println("=              1. Register User         =");
            System.out.println("=              2. Login                 =");
            System.out.println("=              3. Exit                  =");
            System.out.println("=========================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userView.registerUser();
                    break;
                case 2:
                    userView.loginUser();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

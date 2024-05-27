package com.example.auction_app.views;

import com.example.auction_app.models.Product;
import com.example.auction_app.models.User;
import com.example.auction_app.services.ProductService;

import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ProductView {
    private final ProductService productService;
    private final Scanner scanner;
    private final User loggedInUser;

    public ProductView(Scanner scanner, User loggedInUser) {
        this.productService = new ProductService();
        this.scanner = scanner;
        this.loggedInUser = loggedInUser;
    }

    public void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Product Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Product Condition: ");
        String condition = scanner.nextLine();
        System.out.print("Enter Image URL: ");
        String imageUrl = scanner.nextLine();

        Product product = new Product(0, loggedInUser.getUserID(), name, description, category, new java.util.Date(), condition, imageUrl);
        productService.addProduct(product);
        System.out.println("Product added successfully.");
    }

    public void updateProduct() {
        List<Product> products = productService.getAllProducts();
        Map<Integer, Product> userProductsMap = new HashMap<>();
        int index = 1;

        System.out.println("Your Products:");
        for (Product product : products) {
            if (product.getUserID() == loggedInUser.getUserID()) {
                userProductsMap.put(index, product);
                System.out.println(index + ". " + product.getName() + " (ID: " + product.getProductID() + ")");
                index++;
            }
        }

        if (userProductsMap.isEmpty()) {
            System.out.println("No products found for update.");
            return;
        }

        System.out.print("Select the product number to update: ");
        int productNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Product selectedProduct = userProductsMap.get(productNumber);
        if (selectedProduct == null) {
            System.out.println("Invalid product selection.");
            return;
        }

        System.out.print("Enter new Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Product Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new Product Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter new Product Condition: ");
        String condition = scanner.nextLine();
        System.out.print("Enter new Image URL: ");
        String imageUrl = scanner.nextLine();

        selectedProduct.setName(name);
        selectedProduct.setDescription(description);
        selectedProduct.setCategory(category);
        selectedProduct.setProductCondition(condition);
        selectedProduct.setImageURL(imageUrl);

        productService.updateProduct(selectedProduct);
        System.out.println("Product updated successfully.");
    }

    public void deleteProduct() {
        List<Product> products = productService.getAllProducts();
        Map<Integer, Product> userProductsMap = new HashMap<>();
        int index = 1;

        System.out.println("Your Products:");
        for (Product product : products) {
            if (product.getUserID() == loggedInUser.getUserID()) {
                userProductsMap.put(index, product);
                System.out.println(index + ". " + product.getName() + " (ID: " + product.getProductID() + ")");
                index++;
            }
        }

        if (userProductsMap.isEmpty()) {
            System.out.println("No products found for deletion.");
            return;
        }

        System.out.print("Select the product number to delete: ");
        int productNumber = scanner.nextInt();
        scanner.nextLine();

        Product selectedProduct = userProductsMap.get(productNumber);
        if (selectedProduct == null) {
            System.out.println("Invalid product selection.");
            return;
        }

        productService.deleteProduct(selectedProduct);
        System.out.println("Product deleted successfully.");
    }

}

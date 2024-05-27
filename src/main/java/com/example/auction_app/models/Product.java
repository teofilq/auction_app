package com.example.auction_app.models;

import java.util.Date;

public class Product {
    private int productID;
    private int userID;
    private String name;
    private String description;
    private String category;
    private Date addDate;
    private String productCondition;
    private String imageURL;

    public Product() {}

    public Product(int productID, int userID, String name, String description, String category, Date addDate, String productCondition, String imageURL) {
        this.productID = productID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.addDate = addDate;
        this.productCondition = productCondition;
        this.imageURL = imageURL;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

package com.example.auction_app.models;

import java.util.Date;

public class Auction {
    private int auctionID;
    private int productID;
    private double startPrice;
    private double minStep;
    private Date startDate;
    private Date endDate;
    private String status;
    private Integer winnerID;

    public Auction() {}

    public Auction(int auctionID, int productID, double startPrice, double minStep, Date startDate, Date endDate, String status, Integer winnerID) {
        this.auctionID = auctionID;
        this.productID = productID;
        this.startPrice = startPrice;
        this.minStep = minStep;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.winnerID = winnerID;
    }

    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getMinStep() {
        return minStep;
    }

    public void setMinStep(double minStep) {
        this.minStep = minStep;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }
}

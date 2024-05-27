package com.example.auction_app.models;

import java.util.Date;

public class Bid {
    private int bidID;
    private int auctionID;
    private int userID;
    private double bidAmount;
    private Date bidDateTime;

    public Bid() {}

    public Bid(int bidID, int auctionID, int userID, double bidAmount, Date bidDateTime) {
        this.bidID = bidID;
        this.auctionID = auctionID;
        this.userID = userID;
        this.bidAmount = bidAmount;
        this.bidDateTime = bidDateTime;
    }

    public int getBidID() {
        return bidID;
    }

    public void setBidID(int bidID) {
        this.bidID = bidID;
    }

    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Date getBidDateTime() {
        return bidDateTime;
    }

    public void setBidDateTime(Date bidDateTime) {
        this.bidDateTime = bidDateTime;
    }
}

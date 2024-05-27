package com.example.auction_app.services;

import com.example.auction_app.models.Bid;
import com.example.auction_app.persistence.BidRepository;

import java.util.List;

public class BidService {
    private BidRepository bidRepository;

    public BidService() {
        this.bidRepository = new BidRepository();
    }

    public void addBid(Bid bid) {
        bidRepository.add(bid);
    }

    public Bid getBid(int id) {
        return bidRepository.get(id);
    }

    public void updateBid(Bid bid) {
        bidRepository.update(bid);
    }

    public void deleteBid(Bid bid) {
        bidRepository.delete(bid);
    }

    public List<Bid> getAllBids() {
        return bidRepository.getAll();
    }

    public int getBidCount() {
        return bidRepository.getSize();
    }
}

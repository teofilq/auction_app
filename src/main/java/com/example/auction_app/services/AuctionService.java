package com.example.auction_app.services;

import com.example.auction_app.models.Auction;

import com.example.auction_app.persistence.AuctionRepository;
import com.example.auction_app.services.AuditService;
import java.sql.SQLException;
import java.util.List;

public class AuctionService {
    private AuctionRepository auctionRepository;
    private AuditService auditService;

    public AuctionService() {
        this.auctionRepository = new AuctionRepository();
        this.auditService = AuditService.getInstance();
    }

    public void addAuction(Auction auction) throws SQLException {
        auctionRepository.add(auction);
        auditService.logAction();
    }
    public Auction getAuction(int id) {
        return auctionRepository.get(id);
    }

    public void updateAuction(Auction auction) {
        auctionRepository.update(auction);
        auditService.logAction();
    }

    public void deleteAuction(Auction auction) {
        auctionRepository.delete(auction);
        auditService.logAction();
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.getAll();
    }

    public int getAuctionCount() {
        return auctionRepository.getSize();
    }
}

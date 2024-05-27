package com.example.auction_app.services;

import com.example.auction_app.models.Product;
import com.example.auction_app.persistence.ProductRepository;
import com.example.auction_app.services.AuditService;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository;
    private AuditService auditService;

    public ProductService() {
        this.productRepository = new ProductRepository();
        this.auditService = AuditService.getInstance();
    }

    public void addProduct(Product product) {
        productRepository.add(product);
        auditService.logAction();
    }

    public Product getProduct(int id) {
        return productRepository.get(id);
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
        auditService.logAction();
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
        auditService.logAction();
    }

    public List<Product> getAllProducts() {
        return productRepository.getAll();
    }

    public int getProductCount() {
        return productRepository.getSize();
    }
}

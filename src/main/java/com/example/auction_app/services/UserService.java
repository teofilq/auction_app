package com.example.auction_app.services;

import com.example.auction_app.models.User;
import com.example.auction_app.persistence.UserRepository;
import com.example.auction_app.services.AuditService;


import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private AuditService auditService;
    public UserService() {
        this.userRepository = new UserRepository();
        this.auditService = AuditService.getInstance();
    }

    public void addUser(User user) throws SQLException {
        userRepository.add(user);
        auditService.logAction();
    }

    public User getUser(int id) {
        return userRepository.get(id);
    }

    public void updateUser(User user) {
        userRepository.update(user);
        auditService.logAction();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
        auditService.logAction();
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
    public int getUserCount() {
        return userRepository.getSize();
    }
}

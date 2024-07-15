package com.example.springsocial.controller;

import com.example.springsocial.model.User;
import com.example.springsocial.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.springsocial.repository.UserRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateUserRoleAndStatus(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> requestBody) {

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) requestBody.get("roles");
        String status = (String) requestBody.get("status");

        System.out.println("Inside updateUserRoleAndStatus for userId: " + userId);
        System.out.println("Roles to be updated: " + roles);
        System.out.println("Status to be updated: " + status);

        try {
            User user = userService.updateUserRoleAndStatus(userId, roles, status);
            System.out.println("Updated user: " + user);
            return ResponseEntity.ok("Role and status updated successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating role and status: " + e.getMessage());
        }
    }
}

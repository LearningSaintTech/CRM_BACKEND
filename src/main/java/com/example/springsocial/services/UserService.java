package com.example.springsocial.services;

import com.example.springsocial.model.User;
import com.example.springsocial.payload.ApiResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public interface UserService {
    User updateUserRoleAndStatus(Long userId, List<String> newRoles, String newStatus);
    
    Optional<User> getUserById(Long id);
    
    ResponseEntity<ApiResponse> findAllUsers();
    
    ResponseEntity<ApiResponse> findAllUsersExceptThisUserId(Long userId);
    
    ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}

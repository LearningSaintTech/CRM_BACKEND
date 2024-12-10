package com.example.springsocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.UserPrincipal;
import com.example.springsocial.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @GetMapping("/user/me")
//    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    	System.out.println("ctvbunimk");
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
    
    @GetMapping("/pri")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPrivateEndpoint() {
        return ResponseEntity.ok().body("{\"message\": \"Hello, ! You pri have access to this private endpoint.\"}");
    }
    
    @GetMapping("/prii")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getPrivateEndpointt() {
        return ResponseEntity.ok().body("{\"message\": \"Hello, ! You prii have access to this private endpoint.\"}");
    }
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAllUsers() {
        return userService.findAllUsers();
    }
    
    @GetMapping("/conversation/id")
    public ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return userService.findConversationIdByUser1IdAndUser2Id(user1Id, user2Id);
    }
    
    @GetMapping("/except/{userId}")
    public ResponseEntity<ApiResponse> findAllUsersExceptThisUserId(@PathVariable Long userId) {
        return userService.findAllUsersExceptThisUserId(userId);
    }
}

package com.example.springsocial.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.Conversation;
import com.example.springsocial.model.Role;
import com.example.springsocial.model.User;
import com.example.springsocial.model.UserProfile;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.repository.ConversationRepository;
import com.example.springsocial.repository.RoleRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

	private ConversationRepository conversationRepository;

    @Override
    @Transactional
    public User updateUserRoleAndStatus(Long userId, List<String> newRoles, String newStatus) {
        System.out.println("Inside updateUserRoleAndStatus for userId: " + userId);
        System.out.println("Roles to be updated: " + newRoles);
        System.out.println("Status to be updated: " + newStatus);

        // Fetch the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Fetch the roles by name
        List<Role> roles = newRoles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName)))
                .collect(Collectors.toList());

        System.out.println("Roles found: " + roles);

        // Update user roles and status
        user.setRoles(roles);
        user.setStatus(newStatus);
        System.out.println("User details before saving: " + user);

        // Save the updated user
        User updatedUser = userRepository.save(user);
        System.out.println("User details after saving: " + updatedUser);

        return updatedUser;
    }
    public UserProfile getUserProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Retrieve and return the associated UserProfile
        UserProfile userProfile = user.getUserProfile();
        if (userProfile == null) {
            throw new EntityNotFoundException("UserProfile not found for User ID: " + userId);
        }
        
        return userProfile;
    }
    
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  // Uses UserRepository to get the user by ID
    }
    
    @Override
    public ResponseEntity<ApiResponse> findAllUsers() {
        List<User> list = userRepository.findAll();
        
        // Use the manual builder
        ApiResponse apiResponse = new ApiResponse.Builder()
                .statusCode(200)
                .status("Success")
                .reason("OK")
                .data(list)
                .build();
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> findAllUsersExceptThisUserId(Long userId) {
        List<User> list = userRepository.findAllUsersExceptThisUserId(userId);

        // Use the manual builder
        ApiResponse apiResponse = new ApiResponse.Builder()
                .statusCode(200)
                .status("Success")
                .reason("OK")
                .data(list)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        int conversationId;

        // Check if users exist
        Optional<User> user1 = userRepository.findById(user1Id);
        Optional<User> user2 = userRepository.findById(user2Id);

        if (user1.isEmpty() || user2.isEmpty()) {
            // Create ApiResponse for user not found
            ApiResponse errorResponse = new ApiResponse.Builder()
                    .statusCode(404)
                    .status("Failed")
                    .reason("User not found")
                    .data(null)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // Check if the conversation exists
        Optional<Conversation> existingConversation = conversationRepository.findConversationByUsers(user1.get(), user2.get());

        if (existingConversation.isPresent()) {
            // Get the existing conversation ID
            conversationId = existingConversation.get().getConversationId();
        } else {
            // Create a new conversation if not present
            Conversation newConversation = new Conversation();
            newConversation.setUser1(user1.get());
            newConversation.setUser2(user2.get());
            Conversation savedConversation = conversationRepository.save(newConversation);
            conversationId = savedConversation.getConversationId();
        }

        // Create ApiResponse for success
        ApiResponse successResponse = new ApiResponse.Builder()
                .statusCode(200)
                .status("Success")
                .reason("OK")
                .data(conversationId)
                .build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}

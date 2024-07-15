package com.example.springsocial.servicesImpl;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.Role;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.RoleRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
}

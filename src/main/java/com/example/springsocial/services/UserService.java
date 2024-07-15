package com.example.springsocial.services;

import com.example.springsocial.model.User;
import java.util.List;

public interface UserService {
    User updateUserRoleAndStatus(Long userId, List<String> newRoles, String newStatus);
}

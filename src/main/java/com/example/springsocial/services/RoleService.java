package com.example.springsocial.services;

import com.example.springsocial.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> findByName(String name);
    Optional<Role> findById(int roleId);
}

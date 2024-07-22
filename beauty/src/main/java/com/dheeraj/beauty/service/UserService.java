package com.dheeraj.beauty.service;

import com.dheeraj.beauty.model.Role;
import com.dheeraj.beauty.model.User;
import com.dheeraj.beauty.repository.RoleRepository;
import com.dheeraj.beauty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        userRole.ifPresent(roles::add);
        user.setRoles(roles);
        userRepository.save(user);
    }
}

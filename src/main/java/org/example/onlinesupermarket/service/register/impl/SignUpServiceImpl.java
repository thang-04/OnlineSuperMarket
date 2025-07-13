package org.example.onlinesupermarket.service.register.impl;

import org.example.onlinesupermarket.dto.register.SignUpDto;
import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.mapper.register.RegisterMapper;
import org.example.onlinesupermarket.repository.RoleRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.service.register.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RegisterMapper registerMapper;

    public void registerUser(SignUpDto signUpDto) {
        // Validate email availability
        isEmailAvailable(signUpDto);

        // Get default user role (assuming "USER" role exists)
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Default user role not found"));

        // Create new user
        User newUser = registerMapper.toEntity(signUpDto, userRole);

        try {
            User savedUser = userRepository.save(newUser);
            System.out.println("User saved successfully with ID: " + savedUser.getUserId());
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    public boolean isEmailAvailable(SignUpDto signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new RuntimeException("Email address is already registered");
        }
        return true;
    }
}

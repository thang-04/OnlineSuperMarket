package org.example.onlinesupermarket.mapper.register;

import org.example.onlinesupermarket.dto.register.SignUpDto;
import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    
    private final PasswordEncoder passwordEncoder;
    
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public User toEntity(SignUpDto signUpDto, Role role) {
        User user = new User();
        user.setFullName(signUpDto.getFullName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user.setLocked(false);
        return user;
    }
    
    public SignUpDto toDto(User user) {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setFullName(user.getFullName());
        signUpDto.setEmail(user.getEmail());
        signUpDto.setPhoneNumber(user.getPhoneNumber());
        return signUpDto;
    }
} 
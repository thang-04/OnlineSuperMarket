package org.example.onlinesupermarket.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.entity.User;

import org.example.onlinesupermarket.mapper.user.UserMapper;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.service.uploadFIle.FileUploadService;
import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileUploadService fileUploadService;


    @Override
    public UserDTO getCurrentUserDTO() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return userMapper.toDTO(user);
    }

    @Override
    public void updateCurrentUserProfile(String fullName, String phoneNumber, MultipartFile profileImage) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        // Validate input
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }

        // Update basic information
        user.setFullName(fullName.trim());
        user.setPhoneNumber(phoneNumber != null ? phoneNumber.trim() : null);

        // Handle profile image upload
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String imageUrl = fileUploadService.uploadProfileImage(profileImage);
                user.setUserImg(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile image: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid image file: " + e.getMessage(), e);
            }
        }
        userRepository.save(user);
    }
}

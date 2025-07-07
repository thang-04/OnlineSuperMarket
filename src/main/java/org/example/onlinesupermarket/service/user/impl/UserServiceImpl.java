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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileUploadService fileUploadService;


    @Override
    public UserDTO getCurrentUserDTO() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }

    @Override
    public void updateCurrentUserProfile(String fullName, String phoneNumber, MultipartFile profileImage) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        logger.info("[ProfileUpdate] Updating profile for user: {}", email);
        // Validate input
        if (fullName == null || fullName.trim().isEmpty()) {
            logger.warn("[ProfileUpdate] Full name is empty");
            throw new IllegalArgumentException("Full name is required");
        }

        // Update basic information
        user.setFullName(fullName.trim());
        user.setPhoneNumber(phoneNumber != null ? phoneNumber.trim() : null);

        // Handle profile image upload
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                logger.info("[ProfileUpdate] Uploading new profile image for user: {}", email);
                String imageUrl = fileUploadService.uploadProfileImage(profileImage);
                user.setUserImg(imageUrl);
                logger.info("[ProfileUpdate] New profile image URL: {}", imageUrl);
            } catch (IOException e) {
                logger.error("[ProfileUpdate] Failed to upload profile image: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to upload profile image: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                logger.error("[ProfileUpdate] Invalid image file: {}", e.getMessage(), e);
                throw new RuntimeException("Invalid image file: " + e.getMessage(), e);
            }
        } else {
            logger.info("[ProfileUpdate] No new profile image uploaded for user: {}", email);
        }
        userRepository.save(user);
        logger.info("[ProfileUpdate] User profile saved for user: {}", email);
    }
}

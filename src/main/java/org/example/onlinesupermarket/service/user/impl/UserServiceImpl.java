package org.example.onlinesupermarket.service.user.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.mapper.user.UserMapper;
import org.example.onlinesupermarket.repository.RoleRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.service.file.FileService;
import org.example.onlinesupermarket.service.uploadFIle.FileUploadService;
import org.example.onlinesupermarket.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final FileUploadService fileUploadService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;


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

        if (fullName == null || fullName.trim().isEmpty()) {
            logger.warn("[ProfileUpdate] Full name is empty");
            throw new IllegalArgumentException("Full name is required");
        }

        user.setFullName(fullName.trim());
        user.setPhoneNumber(phoneNumber != null ? phoneNumber.trim() : null);

        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String imageUrl = fileUploadService.uploadProfileImage(profileImage);
                user.setUserImg(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile image: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid image file: " + e.getMessage(), e);
            }
        } else {
        }
        userRepository.save(user);
    }

    @Override
    public void createUser(UserDTO userDto, MultipartFile imageFile) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException();
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileService.storeFile(imageFile);
            String imageUrl = "/user-images/" + fileName;
            userDto.setUserImg(imageUrl);
        }

        User user = userMapper.toUser(userDto);
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        user.setPassword(userDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setLocked(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserDTO userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + id));

        existingUser.setEmail(userDto.getEmail());
        existingUser.setFullName(userDto.getFullName());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setLocked(userDto.isLocked());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(userDto.getPassword());
        }

        if (userDto.getRoleId() != null && !userDto.getRoleId().equals(existingUser.getRole().getRoleId())) {
            Role role = roleRepository.findById(userDto.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vai trò với ID: " + userDto.getRoleId()));
            existingUser.setRole(role);
        }

        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy người dùng với ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailExits(String email) {
        if (!StringUtils.hasText(email)) {
            return false;
        }
        String trimmedEmail = email.trim();
        return userRepository.existsByEmailIgnoreCase(trimmedEmail);
    }

    @Override
    public Page<UserDTO> getUsers(String keyword, Integer roleId, Pageable pageable) {
        Page<User> userPage = userRepository.findByFilters(keyword, roleId, pageable);
        return userPage.map(userMapper::toDTO);
    }
}
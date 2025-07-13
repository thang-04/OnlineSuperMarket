package org.example.onlinesupermarket.service.user.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.mapper.user.UserMapper;
import org.example.onlinesupermarket.repository.RoleRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.service.file.FileService;
import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

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
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEmailExits(String emamil) {
        if(userRepository.existsByEmail(emamil)){
            return false;
        }
        return  true;
    }
}
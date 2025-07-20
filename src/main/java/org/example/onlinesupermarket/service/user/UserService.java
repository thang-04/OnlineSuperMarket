package org.example.onlinesupermarket.service.user;

import org.example.onlinesupermarket.dto.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.example.onlinesupermarket.dto.user.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserDTO getCurrentUserDTO();
    void updateCurrentUserProfile(String fullName, String phoneNumber, MultipartFile profileImage);
    void updateUser(Integer id, UserDTO userDto);
    void deleteUser(Integer id);
    UserDTO getUserById(Integer id);
    void createUser(UserDTO userDto, MultipartFile imageFile);
    List<UserDTO> getAllUsers();
    boolean isEmailExits(String emamil);

    Page<UserDTO> getUsers(String keyword, Integer roleId, Pageable pageable);
}

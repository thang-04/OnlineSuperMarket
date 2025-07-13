package org.example.onlinesupermarket.service.user;

import org.example.onlinesupermarket.dto.user.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    void updateUser(Integer id, UserDTO userDto);
    void deleteUser(Integer id);
    UserDTO getUserById(Integer id);
    void createUser(UserDTO userDto, MultipartFile imageFile);
    List<UserDTO> getAllUsers();
    boolean isEmailExits(String emamil);
}
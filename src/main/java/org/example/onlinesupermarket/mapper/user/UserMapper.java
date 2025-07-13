package org.example.onlinesupermarket.mapper.user;

import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setUserImg(user.getUserImg());
        if (user.getRole() != null) {
            userDTO.setRoleId(user.getRole().getRoleId());
            userDTO.setRoleName(user.getRole().getRoleName());
        }
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLocked(user.isLocked());
        return userDTO;
    }

    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserImg(userDTO.getUserImg());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setLocked(userDTO.isLocked());
        return user;
    }
}
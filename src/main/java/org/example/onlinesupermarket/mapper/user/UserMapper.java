package org.example.onlinesupermarket.mapper.user;

import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setUserImg(user.getUserImg());
        dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
        dto.setLocked(user.isLocked());
        dto.setAddress(user.getAddresses().stream()
                .findFirst()
                .orElse(null)); // Assuming you want the first address if multiple exist
        return dto;
    }
    
    public void updateEntity(UserDTO dto, User user) {
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserImg(dto.getUserImg());
    }
} 
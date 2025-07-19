package org.example.onlinesupermarket.service.user;

import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    UserDTO getCurrentUserDTO();
    void updateCurrentUserProfile(String fullName, String phoneNumber, MultipartFile profileImage);
    User findByEmail(String email);
}

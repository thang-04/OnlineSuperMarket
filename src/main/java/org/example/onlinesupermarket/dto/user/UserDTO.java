package org.example.onlinesupermarket.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinesupermarket.entity.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String phoneNumber;
    private String userImg;
    private String roleName;
    private boolean isLocked;
    private Address address;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @NotNull(message = "Please select a role")
    private Integer roleId;
    private LocalDateTime createdAt;
    private MultipartFile imageFile;
}
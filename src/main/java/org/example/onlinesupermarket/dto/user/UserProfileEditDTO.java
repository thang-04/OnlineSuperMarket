package org.example.onlinesupermarket.dto.user;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class UserProfileEditDTO {
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @Pattern(regexp = "^(|[0-9]{10,11})$", message = "Phone number must be 10-11 digits or empty")
    private String phoneNumber;

    private String userImg;
} 
package org.example.onlinesupermarket.dto.user;

import lombok.Data;
import org.example.onlinesupermarket.entity.Address;

@Data
public class UserDTO {
    private Integer userId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String userImg;
    private String roleName;
    private boolean isLocked;
    private Address address;
}

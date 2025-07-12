package org.example.onlinesupermarket.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinesupermarket.entity.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

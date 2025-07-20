package org.example.onlinesupermarket.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer roleId;
    private String roleName;
}
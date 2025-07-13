// src/main/java/org/example/onlinesupermarket/mapper/role/RoleMapper.java
package org.example.onlinesupermarket.mapper.role;

import org.example.onlinesupermarket.dto.role.RoleDTO;
import org.example.onlinesupermarket.entity.Permission;
import org.example.onlinesupermarket.entity.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleDTO toRoleDTO(Role role) {
        if (role == null) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(role.getRoleId());
        roleDTO.setRoleName(role.getRoleName());

        if (role.getPermissions() != null) {
            roleDTO.setPermissionIds(
                    role.getPermissions().stream()
                            .map(Permission::getId)
                            .collect(Collectors.toSet())
            );
        }

        return roleDTO;
    }

    public Role toRole(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }

        Role role = new Role();
        role.setRoleId(roleDTO.getRoleId());
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }
}
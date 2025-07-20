package org.example.onlinesupermarket.mapper.permission;

import org.example.onlinesupermarket.dto.permission.PermissionDTO;
import org.example.onlinesupermarket.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public PermissionDTO toPermissionDTO(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionDTO dto = new PermissionDTO();
        dto.setId(permission.getId());
        dto.setName(permission.getName());
        return dto;
    }
}
package org.example.onlinesupermarket.service.permission;

import org.example.onlinesupermarket.dto.permission.PermissionDTO;

import java.util.List;

public interface PermissionService {
    List<PermissionDTO> findAllPermissions();

    void save(PermissionDTO permissionDTO);

    void deleteById(Integer id);
}

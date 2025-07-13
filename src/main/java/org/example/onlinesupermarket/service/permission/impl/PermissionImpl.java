package org.example.onlinesupermarket.service.permission.impl;

import org.example.onlinesupermarket.dto.permission.PermissionDTO;
import org.example.onlinesupermarket.entity.Permission;
import org.example.onlinesupermarket.mapper.permission.PermissionMapper;
import org.example.onlinesupermarket.repository.PermissionRepository;
import org.example.onlinesupermarket.repository.RoleRepository;
import org.example.onlinesupermarket.service.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<PermissionDTO> findAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(PermissionDTO permissionDTO) {
        Permission permission;
        if (permissionDTO.getId() != null) {
            permission = permissionRepository.findById(permissionDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Permission not found"));
        } else {
            permission = new Permission();
        }
        permission.setName(permissionDTO.getName());
        permissionRepository.save(permission);
    }

    @Override
    public void deleteById(Integer id) {
        long roleCount = roleRepository.countByPermissions_Id(id);
        if (roleCount > 0) {
            throw new IllegalStateException("Không thể xóa quyền này vì đang được " + roleCount + " vai trò sử dụng.");
        }
        permissionRepository.deleteById(id);
    }
}
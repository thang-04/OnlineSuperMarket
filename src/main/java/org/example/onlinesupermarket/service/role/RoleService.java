// src/main/java/org/example/onlinesupermarket/service/role/RoleService.java
package org.example.onlinesupermarket.service.role;

import org.example.onlinesupermarket.dto.role.RoleDTO;
import org.example.onlinesupermarket.entity.Role;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    Role findById(Integer id);
    Role save(RoleDTO roleDTO);
    void deleteById(Integer id);
}
// src/main/java/org/example/onlinesupermarket/service/role/imple/RoleServiceImple.java
package org.example.onlinesupermarket.service.role.imple;

import org.example.onlinesupermarket.dto.role.RoleDTO;
import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.mapper.role.RoleMapper;
import org.example.onlinesupermarket.repository.RoleRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImple implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleDTO)
                .toList();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        long userCount = userRepository.countByRole_RoleId(id);

        if (userCount > 0) {
            throw new IllegalStateException("Không thể xóa vai trò này vì đang được " + userCount + " người dùng sử dụng.");
        }

        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Role save(RoleDTO roleDTO) {
        Role role;
        if (roleDTO.getRoleId() != null) {
            role = roleRepository.findById(roleDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò với ID: " + roleDTO.getRoleId()));
        } else {
            role = new Role();
        }

        role.setRoleName(roleDTO.getRoleName());

        return roleRepository.save(role);
    }
}
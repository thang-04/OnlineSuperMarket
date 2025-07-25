package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleId(Integer roleId);

    Optional<Role> findByRoleName(String roleName);
}
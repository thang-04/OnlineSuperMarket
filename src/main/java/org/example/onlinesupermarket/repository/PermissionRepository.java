package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}

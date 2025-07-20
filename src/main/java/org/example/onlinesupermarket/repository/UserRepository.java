package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(Integer userId);
    @Query("select p from User p where p.userId = :userId")
    Optional<User> getUserId(@Param("userId") Integer userId);

    boolean existsByEmail(String email);

    long countByRole_RoleId(Integer roleRoleId);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isLocked = false")
    Optional<User> findByEmailAndNotLocked(@Param("email") String email);

    boolean existsByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE " +
            "(:keyword IS NULL OR u.fullName LIKE %:keyword% OR u.email LIKE %:keyword%) AND " +
            "(:roleId IS NULL OR u.role.roleId = :roleId)")
    Page<User> findByFilters(@Param("keyword") String keyword,
                             @Param("roleId") Integer roleId,
                             Pageable pageable);

}

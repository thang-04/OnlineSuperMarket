package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    long countByRole_RoleId(Integer roleRoleId);
    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Integer userId);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isLocked = false")
    Optional<User> findByEmailAndNotLocked(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean isEmailExists(@Param("email") String email);
}

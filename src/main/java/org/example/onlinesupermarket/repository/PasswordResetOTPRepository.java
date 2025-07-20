package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.PasswordResetOTP;
import org.example.onlinesupermarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetOTPRepository extends JpaRepository<PasswordResetOTP, Long> {
    Optional<PasswordResetOTP> findByUser(User user);

    void deleteByExpiryTimeBefore(LocalDateTime expiryTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordResetOTP p WHERE p.user.userId = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
} 
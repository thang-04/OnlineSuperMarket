package org.example.onlinesupermarket.service.email;

import org.example.onlinesupermarket.entity.PasswordResetOTP;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.repository.PasswordResetOTPRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PasswordResetService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetOTPRepository otpRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int MAX_OTP_ATTEMPTS = 3;

    @Transactional
    public void generateOTP(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Email does not exist"));
        deleteExistingOTP(user.getUserId());
        String otp = generateSecureOTP();
        PasswordResetOTP resetOtp = new PasswordResetOTP();
        resetOtp.setUser(user);
        resetOtp.setOtp(otp);
        resetOtp.setExpiryTime(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        resetOtp.setAttempts(0);
        resetOtp.setCreatedAt(LocalDateTime.now());
        otpRepository.save(resetOtp);
        String subject = "Password Reset OTP";
        String message = OTPEmailMessage(user.getFullName(), otp);
        try {
            emailService.sendOTP(user.getEmail(), subject, message);
        } catch (Exception e) {
            otpRepository.delete(resetOtp);
            throw new RuntimeException("Could not send email. Please try again later.");
        }
    }

    @Transactional
    public void deleteExistingOTP(Integer userId) {
        try {
            otpRepository.deleteByUserId(userId);
        } catch (Exception e) {
            System.err.println("Error deleting old OTP for user " + userId + ": " + e.getMessage());
        }
    }

    public boolean verifyOTP(String email, String inputOtp) {
        if (email == null || inputOtp == null || inputOtp.trim().isEmpty()) {
            return false;
        }
        User user = userRepository.findByEmail(email.trim().toLowerCase()).orElse(null);
        if (user == null) {
            return false;
        }
        Optional<PasswordResetOTP> resetOtpOpt = otpRepository.findByUser(user);
        if (resetOtpOpt.isEmpty()) {
            return false;
        }
        PasswordResetOTP resetOtp = resetOtpOpt.get();
        if (resetOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpRepository.delete(resetOtp);
            return false;
        }
        if (resetOtp.getAttempts() >= MAX_OTP_ATTEMPTS) {
            otpRepository.delete(resetOtp);
            return false;
        }
        resetOtp.setAttempts(resetOtp.getAttempts() + 1);
        otpRepository.save(resetOtp);
        if (!resetOtp.getOtp().equals(inputOtp.trim())) {
            return false;
        }
        resetOtp.setVerified(true);
        otpRepository.save(resetOtp);
        return true;
    }

    public String resetPassword(String email, String otp, String newPassword) {
        if (email == null || otp == null || newPassword == null) {
            return "Invalid information!";
        }
        if (newPassword.trim().length() < 8) {
            return "Password must be at least 8 characters!";
        }
        User user = userRepository.findByEmail(email.trim().toLowerCase()).orElse(null);
        if (user == null) {
            return "Email does not exist!";
        }
        PasswordResetOTP resetOtp = otpRepository.findByUser(user).orElse(null);
        if (resetOtp == null) {
            return "OTP not found or expired!";
        }
        if (resetOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpRepository.delete(resetOtp);
            return "OTP has expired!";
        }
        if (!resetOtp.getOtp().equals(otp.trim())) {
            return "Incorrect OTP!";
        }
        if (!resetOtp.isVerified()) {
            return "OTP not verified!";
        }
        try {
            user.setPassword(passwordEncoder.encode(newPassword.trim()));
            userRepository.save(user);
            emailService.sendPasswordChangedEmail(user);
            otpRepository.delete(resetOtp);
            return "Password changed successfully!";
        } catch (Exception e) {
            return "Error changing password. Please try again!";
        }
    }

    public boolean hasPendingOTP(String email) {
        User user = userRepository.findByEmail(email.trim().toLowerCase()).orElse(null);
        if (user == null) {
            return false;
        }
        Optional<PasswordResetOTP> resetOtp = otpRepository.findByUser(user);
        return resetOtp.isPresent() && resetOtp.get().getExpiryTime().isAfter(LocalDateTime.now());
    }

    private String generateSecureOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private String OTPEmailMessage(String fullName, String otp) {
        return String.format(
                "Hello %s,\n\nYou have requested to reset your password.\n\nYour OTP code is: %s\n\nThis code is valid for %d minutes.\n\nIf you did not request a password reset, please ignore this email.\n\nBest regards,\nOnlineSupermarket Team",
                fullName != null ? fullName : "User",
                otp,
                OTP_EXPIRY_MINUTES
        );
    }
} 
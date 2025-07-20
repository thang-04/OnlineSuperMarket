package org.example.onlinesupermarket.controller.login;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.register.SignUpDto;
import org.example.onlinesupermarket.service.register.SignUpService;
import org.example.onlinesupermarket.service.email.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.entity.User;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class LoginController {

    @Autowired
    private SignUpService signUpService;
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "expired", required = false) String expired,
                            Model model,
                            HttpServletRequest request) {

        if (error != null) {
            // Copy errorMessage from session to model if present
            Object errorMsg = request.getSession().getAttribute("errorMessage");
            if (errorMsg != null) {
                model.addAttribute("errorMessage", errorMsg);
                request.getSession().removeAttribute("errorMessage");
            } else {
                model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            }
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Đăng xuất thành công!");
        }
        if (expired != null) {
            model.addAttribute("warningMessage", "Phiên đăng nhập đã hết hạn!");
        }
        return "loginPage/sign_in";
    }

    @GetMapping("/403")
    public String showAccessDenied() {
        return "error/403";
    }


    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        if (!model.containsAttribute("signUpDto")) {
            model.addAttribute("signUpDto", new SignUpDto());
        }
        return "loginPage/sign_up";
    }

    @PostMapping("/sign-up")
    public String processSignUp(@Valid @ModelAttribute("signUpDto") SignUpDto signUpDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("signUpDto", signUpDto);
            return "loginPage/sign_up";
        }
        try {
            signUpService.registerUser(signUpDto);

        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();

            if (errorMessage.contains("Email address is already registered")) {
                bindingResult.rejectValue("email", "email.duplicate", "Email address is already registered");
            } else if (errorMessage.contains("Passwords do not match")) {
                bindingResult.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
            } else {
                model.addAttribute("error", errorMessage);
            }
            return "loginPage/sign_up";

        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "loginPage/sign_up";

        }
        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng kiểm tra email để xác thực.");
        return "redirect:/otp-verification?email=" + signUpDto.getEmail();
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        if (!model.containsAttribute("email")) {
            model.addAttribute("email", "");
        }
        return "loginPage/forgot_password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            if (passwordResetService.hasPendingOTP(email)) {
                redirectAttributes.addFlashAttribute("errorMessage", "You already have a pending OTP. Please check your email.");
                return "redirect:/otp-verification?email=" + email;
            }
            passwordResetService.generateOTP(email);
            redirectAttributes.addFlashAttribute("successMessage", "OTP has been sent to your email. Please check your inbox.");
            return "redirect:/otp-verification?email=" + email;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/forgot-password";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. Please try again later.");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/otp-verification")
    public String getOtpVerificationPage(@RequestParam("email") String email,
                                         @RequestParam(value = "error", required = false) String error,
                                         Model model) {
        model.addAttribute("email", email);
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid or expired OTP.");
        }
        return "loginPage/otp_verification";
    }

    @PostMapping("/otp-verification")
    public String verifyOtp(@RequestParam("email") String email,
                            @RequestParam("otp") String otp,
                            Model model, RedirectAttributes redirectAttributes) {
        if (passwordResetService.verifyOTP(email, otp)) {
            // Mark email as verified
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                user.setEmailVerified(true);
                userRepository.save(user);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Xác thực email thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid or expired OTP.");
            return "redirect:/otp-verification?email=" + email + "&error=true";
        }
    }

    @GetMapping("/reset-password")
    public String getResetPasswordPage(@RequestParam String email,
                                       @RequestParam String otp,
                                       Model model) {
        model.addAttribute("email", email);
        model.addAttribute("otp", otp);
        return "loginPage/reset_password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String email,
                                      @RequestParam String otp,
                                      @RequestParam String newPassword,
                                      @RequestParam String confirmPassword,
                                      RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match!");
            return "redirect:/reset-password?email=" + email + "&otp=" + otp;
        }
        String result = passwordResetService.resetPassword(email, otp, newPassword);
        if (result.equals("Password changed successfully!")) {
            redirectAttributes.addFlashAttribute("successMessage", result);
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", result);
            return "redirect:/reset-password?email=" + email + "&otp=" + otp;
        }
    }
}


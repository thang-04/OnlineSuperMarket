package org.example.onlinesupermarket.controller.login;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.register.SignUpDto;
import org.example.onlinesupermarket.service.register.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;


@Controller
public class LoginController {

    @Autowired
    private SignUpService signUpService;

        @GetMapping("/login")
        public String loginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "expired", required = false) String expired,
                                Model model) {

            if (error != null) {
                model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            }
            if (logout != null) {
                model.addAttribute("successMessage", "Đăng xuất thành công!");
            }
            if (expired != null) {
                model.addAttribute("warningMessage", "Phiên đăng nhập đã hết hạn!");
            }
            return "homePage/sign_in";
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
        return "homePage/sign_up";
    }

    @PostMapping("/sign-up")
    public String processSignUp(@Valid @ModelAttribute("signUpDto") SignUpDto signUpDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("signUpDto", signUpDto);
            return "homePage/sign_up";
        }
try {
    signUpService.registerUser(signUpDto);

}catch (RuntimeException e) {
    String errorMessage = e.getMessage();

    if (errorMessage.contains("Email address is already registered")) {
        bindingResult.rejectValue("email", "email.duplicate", "Email address is already registered");
    } else if (errorMessage.contains("Passwords do not match")) {
        bindingResult.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
    } else {
        model.addAttribute("error", errorMessage);
    }
    return "homePage/sign_up";

} catch (Exception e) {
    model.addAttribute("error", "An unexpected error occurred. Please try again.");
    return "homePage/sign_up";

}
redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login";
    }

    }


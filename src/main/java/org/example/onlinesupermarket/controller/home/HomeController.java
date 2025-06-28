package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String getHomePage(Model model) {
        model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
        return "homePage/index";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        model.addAttribute("user", userService.getCurrentUserDTO());
        model.addAttribute("fragmentContent", "homePage/fragments/profileContent :: profileContent");
        return "homePage/index";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam("fullName") String fullName,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam(value = "userImg", required = false) MultipartFile profileImage,
                                RedirectAttributes redirectAttributes) {
        try {
            userService.updateCurrentUserProfile(fullName, phoneNumber, profileImage);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/home/profile";
    }
}

package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.dto.address.AddressDTO;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.security.CustomUserDetails;
import org.example.onlinesupermarket.service.address.AddressService;
import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.onlinesupermarket.dto.user.UserDTO;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.user.UserProfileEditDTO;
import org.example.onlinesupermarket.security.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

@Controller
@RequestMapping("/home/profile")
public class ProfileController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping()
    public String showProfile(Model model) {
        User currentUser = getCurrentUser();
        List<AddressDTO> addressList = addressService.getAllAddressesByUser(currentUser);
        AddressDTO defaultAddress = addressService.getDefaultAddress(currentUser);
        UserDTO userDTO = userService.getCurrentUserDTO();
        UserProfileEditDTO profileEditDTO = new UserProfileEditDTO();
        profileEditDTO.setFullName(userDTO.getFullName());
        profileEditDTO.setPhoneNumber(userDTO.getPhoneNumber());
        profileEditDTO.setUserImg(userDTO.getUserImg());
        model.addAttribute("userProfileEditDTO", profileEditDTO);
        model.addAttribute("addressList", addressList);
        model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("fragmentContent", "homePage/fragments/profileContent :: profileContent");
        return "homePage/index";
    }

    @PostMapping()
    public String updateProfile(
            @Valid @ModelAttribute("userProfileEditDTO") UserProfileEditDTO userProfileEditDTO,
            BindingResult bindingResult,
            @RequestParam(value = "userImgFile", required = false) MultipartFile profileImage,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder("Please correct the errors in the form. ");
            bindingResult.getFieldErrors().forEach(error -> {
                errorMsg.append("[")
                        .append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("] ");
            });
            if (userProfileEditDTO.getUserImg() == null || userProfileEditDTO.getUserImg().isEmpty()) {
                UserDTO currentUserDTO = userService.getCurrentUserDTO();
                userProfileEditDTO.setUserImg(currentUserDTO.getUserImg());
            }
            User currentUser = getCurrentUser();
            List<AddressDTO> addressList = addressService.getAllAddressesByUser(currentUser);
            AddressDTO defaultAddress = addressService.getDefaultAddress(currentUser);
            model.addAttribute("userProfileEditDTO", userProfileEditDTO);
            model.addAttribute("addressList", addressList);
            model.addAttribute("defaultAddress", defaultAddress);
            model.addAttribute("fragmentContent", "homePage/fragments/profileContent :: profileContent");
            model.addAttribute("errorMessage", errorMsg.toString());
            return "homePage/index";
        }
        try {
            userService.updateCurrentUserProfile(userProfileEditDTO.getFullName(), userProfileEditDTO.getPhoneNumber(), profileImage);
            User currentUser = getCurrentUser();
            UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(currentUser.getEmail());
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                updatedUserDetails.getPassword(),
                updatedUserDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/home/profile";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUserEntity();
        }
        throw new RuntimeException("User not authenticated");
    }
} 
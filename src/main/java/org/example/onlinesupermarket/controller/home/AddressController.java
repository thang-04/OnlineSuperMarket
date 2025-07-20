package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.dto.address.AddressDTO;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.security.CustomUserDetails;
import org.example.onlinesupermarket.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
@RequestMapping("/home/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public String showAddressPage(Model model) {
        try {
            User currentUser = getCurrentUser();
            List<AddressDTO> addresses = addressService.getAllAddressesByUser(currentUser);
            model.addAttribute("addresses", addresses);
            model.addAttribute("fragmentContent", "homePage/fragments/addressContent :: addressContent");
            model.addAttribute("addressDto", new AddressDTO());
            return "homePage/index";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading addresses: " + e.getMessage());
            model.addAttribute("fragmentContent", "homePage/fragments/addressContent :: addressContent");
            return "homePage/index";
        }
    }

    @PostMapping("/add")
    public String addAddress(@Validated @ModelAttribute("addressDto") AddressDTO addressDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (bindingResult.hasErrors()) {
            // Add validation errors to model
            model.addAttribute("fragmentContent", "homePage/fragments/addressContent :: addressContent");
            model.addAttribute("addresses", addressService.getAllAddressesByUser(getCurrentUser()));
            model.addAttribute("addressDto", addressDTO); // Keep the entered data
            model.addAttribute("openModal", "add");
            return "homePage/index";
        }

        try {
            User currentUser = getCurrentUser();
            addressService.createAddress((Integer) currentUser.getUserId(), addressDTO);
            redirectAttributes.addFlashAttribute("success", "Address added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding address: " + e.getMessage());
        }
        return "redirect:/home/address";
    }

    @PostMapping("/add-checkout")
    public String addAddressFromCheckout(@Validated @ModelAttribute("addressDto") AddressDTO addressDTO,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {
        if (bindingResult.hasErrors()) {
            // Add validation errors to model
            model.addAttribute("fragmentContent", "homePage/fragments/checkoutContent :: checkoutContent");
            model.addAttribute("addresses", addressService.getAllAddressesByUser(getCurrentUser()));
            model.addAttribute("addressDto", addressDTO); // Keep the entered data
            return "homePage/index";
        }
        try {
            User currentUser = getCurrentUser();
            addressService.createAddress((Integer) currentUser.getUserId(), addressDTO);
            redirectAttributes.addFlashAttribute("success", "Địa chỉ đã được thêm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm địa chỉ: " + e.getMessage());
        }
        return "redirect:/home/orders/checkout";
    }


    @GetMapping("/delete/{addressId}")
    public String deleteAddress(@PathVariable Integer addressId, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            addressService.deleteAddress(addressId, currentUser);
            redirectAttributes.addFlashAttribute("success", "Address deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting address: " + e.getMessage());
        }
        return "redirect:/home/address";
    }

    @GetMapping("/set-default/{addressId}")
    public String setDefaultAddress(@PathVariable Integer addressId,
                                  @RequestParam(value = "redirect", defaultValue = "/home/address") String redirectUrl,
                                  RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            addressService.setDefaultAddress(addressId, currentUser);
            redirectAttributes.addFlashAttribute("success", "Default address updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error setting default address: " + e.getMessage());
        }
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/edit")
    public String editAddress(@Validated @ModelAttribute("addressDto") AddressDTO addressDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (bindingResult.hasErrors()) {
            // Add validation errors to model
            model.addAttribute("fragmentContent", "homePage/fragments/addressContent :: addressContent");
            model.addAttribute("addresses", addressService.getAllAddressesByUser(getCurrentUser()));
            model.addAttribute("addressDto", addressDTO); // Keep the entered data
            model.addAttribute("openModal", "edit");
            return "homePage/index";
        }

        try {
            User currentUser = getCurrentUser();
            addressService.updateAddress((Integer) currentUser.getUserId(), addressDTO);
            redirectAttributes.addFlashAttribute("success", "Address updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating address: " + e.getMessage());
        }
        return "redirect:/home/address";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUserEntity();
        }
        throw new RuntimeException("User not authenticated");
    }
}
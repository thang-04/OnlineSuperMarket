package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.dto.address.AddressDTO;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.security.CustomUserDetails;
import org.example.onlinesupermarket.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addAddress(@ModelAttribute("addressDto") AddressDTO addressDTO, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            addressService.createAddress((Integer) currentUser.getUserId(), addressDTO);
            redirectAttributes.addFlashAttribute("success", "Address added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding address: " + e.getMessage());
        }
        return "redirect:/home/address";
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
    public String setDefaultAddress(@PathVariable Integer addressId, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser();
            addressService.setDefaultAddress(addressId, currentUser);
            redirectAttributes.addFlashAttribute("success", "Default address updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error setting default address: " + e.getMessage());
        }
        return "redirect:/home/address";
    }

    @GetMapping("/edit/{addressId}")
    public String getAddressForEdit(@PathVariable Integer addressId,@ModelAttribute("addressDto") AddressDTO addressDTO , Model model) {
        User currentUser = getCurrentUser();
        try {
            AddressDTO address = addressService.getAddressById(addressId, currentUser);
            model.addAttribute("addressDto", address);
            model.addAttribute("addresses", addressService.getAllAddressesByUser(currentUser));
        } catch (Exception e) {
            model.addAttribute("error", "Error loading address for edit: " + e.getMessage());
        }
        return "homePage/index";
    }

    @GetMapping("/edit/{addressId}")
    @ResponseBody
    public AddressDTO getAddressForEditJson(@PathVariable Integer addressId) {
        User currentUser = getCurrentUser();
        return addressService.getAddressById(addressId, currentUser);
    }

    @PostMapping("/edit")
    public String editAddress(@ModelAttribute("addressDto") AddressDTO addressDTO, RedirectAttributes redirectAttributes) {
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
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUserEntity();
        }
        throw new RuntimeException("User not authenticated");
    }
} 
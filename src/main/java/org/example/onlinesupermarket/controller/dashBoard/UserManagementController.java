package org.example.onlinesupermarket.controller.dashBoard;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.service.role.RoleService;
import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDTO());
        }
        model.addAttribute("roles", roleService.getAllRoles());

        return "dashBoard/customers";
    }
    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") Integer id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "dashBoard/customer_view";
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        return "redirect:/admin/users";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDto,
                             BindingResult bindingResult,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {

        if (!userService.isEmailExits(userDto.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi tồn tại email trong hệ thống");
            return "redirect:/admin/users";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            return "redirect:/admin/users";
        }

        userService.createUser(userDto, imageFile);
        redirectAttributes.addFlashAttribute("message", "Tạo người dùng mới thành công!");
        return "redirect:/admin/users";
    }
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "dashBoard/customer_edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id,
                             @Valid @ModelAttribute("user") UserDTO userDto,
                             BindingResult bindingResult, // Thêm để nhận lỗi
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dashBoard/customer_edit";
        }
        userService.updateUser(id, userDto);
        redirectAttributes.addFlashAttribute("message", "Cập nhật người dùng thành công!");
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công!");
        return "redirect:/admin/users";
    }
}
package org.example.onlinesupermarket.controller.dashBoard;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.user.UserDTO;
import org.example.onlinesupermarket.service.role.RoleService;
import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private static final String CUSTOMER_ROLE_NAME = "Customer";

    @GetMapping
    public String listUsers(Model model,
                            @RequestParam(name = "keyword", required = false) String keyword,
                            @RequestParam(name = "roleId", required = false) Integer roleId,
                            @RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UserDTO> userPage = userService.getUsers(keyword, roleId, pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", userPage.getNumber() + 1);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("keyword", keyword);
        model.addAttribute("roleId", roleId);

        // DÒNG CỰC KỲ QUAN TRỌNG: Đảm bảo "newUser" luôn có trong model cho form tạo mới
        if (!model.containsAttribute("newUser")) {
            model.addAttribute("newUser", new UserDTO());
        }

        return "dashBoard/customers";
    }

    /**
     * Phương thức này xử lý việc tạo user.
     * Nó BẮT BUỘC phải làm việc với đối tượng tên là "newUser".
     */
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("newUser") UserDTO userDto,
                             BindingResult bindingResult,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {

        if (userService.isEmailExits(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email đã tồn tại trong hệ thống.");
        }

        if (bindingResult.hasErrors()) {
            // Khi có lỗi, gửi lại đối tượng "newUser" qua redirect
            redirectAttributes.addFlashAttribute("newUser", userDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUser", bindingResult);
            redirectAttributes.addFlashAttribute("openCreateModal", true);
            return "redirect:/admin/users";
        }

        userService.createUser(userDto, imageFile);
        redirectAttributes.addFlashAttribute("message", "Tạo người dùng mới thành công!");
        return "redirect:/admin/users";
    }

    // Các phương thức edit, update, delete không liên quan đến "newUser"
    // nhưng cần đảm bảo chúng hoạt động đúng
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        UserDTO user = userService.getUserById(id);
        if (CUSTOMER_ROLE_NAME.equalsIgnoreCase(user.getRoleName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể chỉnh sửa người dùng có vai trò là Customer.");
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "dashBoard/customer_edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id,
                             @Valid @ModelAttribute("user") UserDTO userDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        UserDTO userToUpdate = userService.getUserById(id);
        if (CUSTOMER_ROLE_NAME.equalsIgnoreCase(userToUpdate.getRoleName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể cập nhật người dùng có vai trò Customer.");
            return "redirect:/admin/users";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("roles", roleService.getAllRoles());
            return "redirect:/admin/users/edit/" + id;
        }
        userService.updateUser(id, userDto);
        redirectAttributes.addFlashAttribute("message", "Cập nhật người dùng thành công!");
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        UserDTO userToDelete = userService.getUserById(id);
        if (CUSTOMER_ROLE_NAME.equalsIgnoreCase(userToDelete.getRoleName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa người dùng có vai trò là Customer.");
            return "redirect:/admin/users";
        }
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công!");
        return "redirect:/admin/users";
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") Integer id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "dashBoard/customer_view";
    }

    @GetMapping("/create")
    public String showCreateUserForm() {
        return "redirect:/admin/users";
    }
}
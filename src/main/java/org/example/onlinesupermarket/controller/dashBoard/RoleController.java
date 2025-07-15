package org.example.onlinesupermarket.controller.dashBoard;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.permission.PermissionDTO;
import org.example.onlinesupermarket.dto.role.RoleDTO;
import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.mapper.role.RoleMapper;
import org.example.onlinesupermarket.service.permission.PermissionService;
import org.example.onlinesupermarket.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping("")
    public String showRolesList(Model model) {
        List<RoleDTO> roles = roleService.getAllRoles();
        List<PermissionDTO> permissions = permissionService.findAllPermissions();

        model.addAttribute("roles", roles);
        model.addAttribute("allPermissions", permissions);
        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new RoleDTO());
        }

        return "dashBoard/roles-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("role", new RoleDTO());
        model.addAttribute("allPermissions", permissionService.findAllPermissions());
        return "dashBoard/role-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Role role = roleService.findById(id);
        if (role == null) {
            return "redirect:/admin/roles";
        }
        RoleDTO roleDTO = roleMapper.toRoleDTO(role);

        model.addAttribute("role", roleDTO);
        model.addAttribute("allPermissions", permissionService.findAllPermissions());
        return "dashBoard/role-form";
    }

    @PostMapping("/save")
    public String saveRole(@Valid @ModelAttribute("role") RoleDTO roleDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", bindingResult);
            redirectAttributes.addFlashAttribute("role", roleDTO);
            redirectAttributes.addFlashAttribute("allPermissions", permissionService.findAllPermissions());

            if (roleDTO.getRoleId() != null) {
                return "redirect:/admin/roles/edit/" + roleDTO.getRoleId();
            }
            return "redirect:/admin/roles/create";
        }

        roleService.save(roleDTO);
        redirectAttributes.addFlashAttribute("message", "Vai trò đã được lưu thành công!");
        return "redirect:/admin/roles";
    }

    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            roleService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Vai trò đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa vai trò này vì đang có người dùng sử dụng.");
        }
        return "redirect:/admin/roles";
    }
}
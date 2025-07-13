package org.example.onlinesupermarket.controller.dashBoard;

import org.example.onlinesupermarket.dto.permission.PermissionDTO;
import org.example.onlinesupermarket.service.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public String showPermissionsList(Model model) {
        model.addAttribute("permissions", permissionService.findAllPermissions());
        if (!model.containsAttribute("permission")) {
            model.addAttribute("permission", new PermissionDTO());
        }
        return "dashBoard/permissions-list";
    }

    @PostMapping("/save")
    public String savePermission(@ModelAttribute PermissionDTO permissionDTO, RedirectAttributes redirectAttributes) {
        try {
            permissionService.save(permissionDTO);
            redirectAttributes.addFlashAttribute("message", "Quyền đã được lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/permissions";
    }

    @PostMapping("/delete/{id}")
    public String deletePermission(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            permissionService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Quyền đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/permissions";
    }
}
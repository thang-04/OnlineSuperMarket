package org.example.onlinesupermarket.controller.dashBoard;

import org.example.onlinesupermarket.dto.banner.BannerDTO;
import org.example.onlinesupermarket.service.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public String listBanners(Model model) {
        model.addAttribute("banners", bannerService.getAllBanners());
        return "dashBoard/banners";
    }

    @GetMapping("/add")
    public String showAddBannerForm(Model model) {
        model.addAttribute("bannerDto", new BannerDTO());
        return "dashBoard/add-banner";
    }

    @PostMapping("/add")
    public String addBanner(@Valid @ModelAttribute("bannerDto") BannerDTO bannerDto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "dashBoard/banners";
        }

        try {
            bannerService.createBanner(bannerDto);
            redirectAttributes.addFlashAttribute("successMessage", "Banner đã được tạo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tạo banner: " + e.getMessage());
        }

        return "redirect:/admin/banners";
    }

    @GetMapping("/edit/{id}")
    public String showEditBannerForm(@PathVariable Integer id, Model model) {
        try {
            BannerDTO bannerDto = bannerService.getBannerById(id);
            model.addAttribute("bannerDto", bannerDto);
            return "dashBoard/edit-banner";
        } catch (RuntimeException e) {
            return "redirect:/admin/banners";
        }
    }

    @PostMapping("/update/{id}")
    public String updateBanner(@PathVariable Integer id,
                               @Valid @ModelAttribute("bannerDto") BannerDTO bannerDto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "dashBoard/banners";
        }

        try {
            bannerService.updateBanner(id, bannerDto);
            redirectAttributes.addFlashAttribute("successMessage", "Banner đã được cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật banner: " + e.getMessage());
        }

        return "redirect:/admin/banners";
    }

    @PostMapping("/delete/{id}")
    public String deleteBanner(@PathVariable Integer id,
                               RedirectAttributes redirectAttributes) {
        try {
            bannerService.deleteBanner(id);
            redirectAttributes.addFlashAttribute("successMessage", "Banner đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa banner: " + e.getMessage());
        }
        return "redirect:/admin/banners";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleBannerStatus(@PathVariable Integer id,
                                     RedirectAttributes redirectAttributes) {
        try {
            bannerService.toggleBannerStatus(id);
            redirectAttributes.addFlashAttribute("successMessage", "Trạng thái banner đã được cập nhật!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
        }
        return "redirect:/admin/banners";
    }
}
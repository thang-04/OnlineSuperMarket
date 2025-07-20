package org.example.onlinesupermarket.controller.dashBoard;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.banner.BannerDTO;
import org.example.onlinesupermarket.service.banner.BannerService;
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
@RequestMapping("/admin/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public String listBanners(Model model,
                              @RequestParam(name = "title", required = false) String title,
                              @RequestParam(name = "status", required = false) Boolean status,
                              @RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<BannerDTO> bannerPage = bannerService.getBanners(title, status, pageable);

        model.addAttribute("bannerPage", bannerPage);
        model.addAttribute("banners", bannerPage.getContent()); // Để tương thích với code cũ nếu cần
        model.addAttribute("currentPage", page);

        model.addAttribute("title", title);
        model.addAttribute("status", status);

        return "dashBoard/banners";
    }

    @GetMapping("/add")
    public String showAddBannerForm(Model model) {
        if (!model.containsAttribute("bannerDto")) {
            model.addAttribute("bannerDto", new BannerDTO());
        }
        return "dashBoard/add-banner";
    }

    @PostMapping("/add")
    public String addBanner(@Valid @ModelAttribute("bannerDto") BannerDTO bannerDto,
                            BindingResult result,
                            @RequestParam("imageFile") MultipartFile imageFile,
                            RedirectAttributes redirectAttributes) {

        if (imageFile.isEmpty()) {
            result.rejectValue("imageUrl", "error.bannerDto", "Vui lòng chọn một file ảnh để upload.");
        }

        if (result.hasErrors()) {
            return "dashBoard/add-banner";
        }

        try {
            bannerService.createBanner(bannerDto, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Banner đã được tạo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tạo banner: " + e.getMessage());
        }

        return "redirect:/admin/banners";
    }


    @PostMapping("/update/{id}")
    public String updateBanner(@PathVariable Integer id,
                               @Valid @ModelAttribute("bannerDto") BannerDTO bannerDto,
                               BindingResult result,
                               @RequestParam("imageFile") MultipartFile imageFile, // Nhận file upload
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            bannerDto.setBannerId(id);
            return "dashBoard/edit-banner";
        }

        try {
            bannerService.updateBanner(id, bannerDto, imageFile);
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
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa banner này. " + e.getMessage());
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
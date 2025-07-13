package org.example.onlinesupermarket.controller.dashBoard;

import org.example.onlinesupermarket.dto.faq.FAQDTO;
import org.example.onlinesupermarket.service.faq.FAQService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/faqs")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @GetMapping
    public String listFAQs(Model model) {
        model.addAttribute("faqs", faqService.getAllFAQs());
        return "dashBoard/faqs-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("faq", new FAQDTO());
        return "dashBoard/faq-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("faq", faqService.getFAQById(id));
            return "dashBoard/faq-form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy FAQ!");
            return "redirect:/admin/faqs";
        }
    }

    @PostMapping("/save")
    public String saveFAQ(@Valid @ModelAttribute("faq") FAQDTO faqDTO,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "dashBoard/faq-form";
        }
        faqService.saveFAQ(faqDTO);
        redirectAttributes.addFlashAttribute("message", "Lưu FAQ thành công!");
        return "redirect:/admin/faqs";
    }

    @PostMapping("/delete/{id}")
    public String deleteFAQ(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            faqService.deleteFAQ(id);
            redirectAttributes.addFlashAttribute("message", "Xóa FAQ thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa FAQ.");
        }
        return "redirect:/admin/faqs";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            faqService.toggleFAQStatus(id);
            redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật trạng thái.");
        }
        return "redirect:/admin/faqs";
    }
}
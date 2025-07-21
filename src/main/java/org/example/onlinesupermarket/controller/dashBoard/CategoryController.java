package org.example.onlinesupermarket.controller.dashBoard;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.category.CategoryDTO;
import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.mapper.category.CategoryMapper;
import org.example.onlinesupermarket.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "dashBoard/category";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        model.addAttribute("isEdit", false);
        return "dashBoard/add_category";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                              RedirectAttributes redirectAttributes) {
        categoryService.addCategory(categoryDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Category added successfully!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        CategoryDTO category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        model.addAttribute("isEdit", true);
        return "dashBoard/add_category";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Integer id,
                                 @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        categoryDTO.setCategoryId(id);
        categoryService.updateCategory(categoryDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/search")
    public String searchCategories(@RequestParam String keyword, Model model) {
        List<CategoryDTO> results = categoryService.getCategoriesByNameContain(keyword);
        model.addAttribute("categories", results);
        model.addAttribute("keyword", keyword);
        return "dashBoard/category";
    }
}
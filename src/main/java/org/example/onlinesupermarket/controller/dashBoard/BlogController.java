package org.example.onlinesupermarket.controller.dashBoard;

import org.example.onlinesupermarket.dto.blog.BlogDTO;
import org.example.onlinesupermarket.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String listBlogs(Model model) {
        model.addAttribute("blogs", blogService.getAllBlogs());
        return "dashBoard/blogs-list";
    }

    @GetMapping("/form")
    public String showNewBlogForm(Model model) {
        model.addAttribute("blog", new BlogDTO());
        return "dashBoard/blog-form";
    }

    @GetMapping("/form/{id}")
    public String showEditBlogForm(@PathVariable Integer id, Model model) {
        try {
            model.addAttribute("blog", blogService.findById(id));
        } catch (RuntimeException e) {
            return "redirect:/admin/blogs";
        }
        return "dashBoard/blog-form";
    }

    @PostMapping("/save")
    public String saveBlog(@ModelAttribute("blog") BlogDTO blogDTO,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           RedirectAttributes redirectAttributes) {
        blogDTO.setAuthorName("Admin");

        blogService.save(blogDTO, imageFile);
        redirectAttributes.addFlashAttribute("message", "Lưu bài viết thành công!");
        return "redirect:/admin/blogs";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            blogService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa bài viết thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa bài viết.");
        }
        return "redirect:/admin/blogs";
    }
}
package org.example.onlinesupermarket.controller.dashBoard;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.blog.BlogDTO;
import org.example.onlinesupermarket.service.blog.BlogService;
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
@RequestMapping("/admin/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String listBlogs(Model model,
                            @RequestParam(name = "title", required = false) String title,
                            @RequestParam(name = "published", required = false) Boolean published,
                            @RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BlogDTO> blogPage = blogService.getBlogs(title, published, pageable);

        model.addAttribute("blogPage", blogPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("title", title);
        model.addAttribute("published", published);

        return "dashBoard/blogs-list";
    }

    @GetMapping("/form")
    public String showNewBlogForm(Model model) {
        model.addAttribute("blog", new BlogDTO());
        return "dashBoard/blog-form";
    }

    @GetMapping("/form/{id}")
    public String showEditBlogForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("blog", blogService.findById(id));
            return "dashBoard/blog-form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài viết!");
            return "redirect:/admin/blogs";
        }
    }

    @PostMapping("/save")
    public String saveBlog(@Valid @ModelAttribute("blog") BlogDTO blogDTO,
                           BindingResult result,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "dashBoard/blog-form";
        }

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
            // Hiển thị lỗi cụ thể từ service (ví dụ: "Không thể xóa bài viết đã xuất bản")
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/blogs";
    }
}
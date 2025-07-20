package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.dto.blog.BlogDTO;


import org.example.onlinesupermarket.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home/blog")
public class ViewBlogController {

    @Autowired
    private BlogService blogService;


    @GetMapping
    public String listBlogs(@RequestParam(name = "search", required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("blogs", blogService.searchBlogs(search));
        } else {
            model.addAttribute("blogs", blogService.getAllBlogs());
        }
        model.addAttribute("fragmentContent", "homePage/fragments/blogContent :: blogContent");
        return "homePage/index";
    }

    @GetMapping("/{id}")
    public String blogDetail(@PathVariable Integer id, Model model) {
        BlogDTO blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("fragmentContent", "homePage/fragments/blogDetailContent :: blogDetailContent");
        return "homePage/index";
    }
}

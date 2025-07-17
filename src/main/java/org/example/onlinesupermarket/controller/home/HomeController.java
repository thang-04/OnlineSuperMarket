package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.service.category.CategoryService;
import org.example.onlinesupermarket.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    //truy cap home page
    @GetMapping()
    public String getHomePage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<Product> products = productService.getAllProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
        return "homePage/index";
    }

    @GetMapping("/categoryId")
    public String searchByCategoryId(@RequestParam(name="categoryId") Integer categoryId, Model model) {
        List<Product> products = null;
        if(categoryId == 0) {
          products = productService.getAllProducts();
        }
        else {
            products = productService.findByCategoryId(categoryId);
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
        return "homePage/index";
    }
    @GetMapping("/productdetail")
    public String detail(Model model) {
        model.addAttribute("fragmentContent", "homePage/fragments/productdetail :: productdetail");
        return "homePage/index";
    }
}

package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.dto.product.ProductDTO;
import org.example.onlinesupermarket.dto.product.ProductDetailDTO;
import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.service.category.CategoryService;
import org.example.onlinesupermarket.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/search")
    public String searchProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortByPrice", required = false, defaultValue = "asc") String sortByPrice,
            Model model) {
        List<Product> products;
        if (name != null && !name.isEmpty() && minPrice != null && maxPrice != null) {
            products = productService.searchByNameAndPrice(name, minPrice, maxPrice, sortByPrice);
        } else if (name != null && !name.isEmpty()) {
            products = productService.searchByName(name, sortByPrice);
        } else if (minPrice != null && maxPrice != null) {
            products = productService.filterByPrice(minPrice, maxPrice, sortByPrice);
        } else {
            products = productService.getAllProducts(sortByPrice);
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
        return "homePage/index";
    }
    @GetMapping("/productdetail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        ProductDetailDTO detail = productService.getProductDetail(id);
        Category category = categoryService.findByName(detail.getCategoryName());
        Integer categoryId = category.getCategoryId();
        List<ProductDTO> moreProduct = productService.MoreLikeThis(categoryId, id);
        List<ProductDTO> featuredProducts = productService.getTopBestSellingProducts(8); // Lấy top 8 sản phẩm bán chạy
        model.addAttribute("moreProduct", moreProduct);
        model.addAttribute("detail", detail);
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("fragmentContent", "homePage/fragments/productdetail :: productdetail");
        return "homePage/index";
    }
}

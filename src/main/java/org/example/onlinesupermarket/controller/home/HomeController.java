package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.dto.product.ProductDTO;
import org.example.onlinesupermarket.dto.product.ProductDetailDTO;
import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.service.category.CategoryService;
import org.example.onlinesupermarket.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public String getHomePage(
            Model model,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortByPrice", required = false, defaultValue = "asc") String sortByPrice,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo
    ) {
        List<Category> categories = categoryService.getAllCategories();
        int pageSize = 8;
        Page<Product> products = productService.searchProducts(name, categoryId, minPrice, maxPrice, sortByPrice, pageNo, pageSize);
        int totalPage = products.getTotalPages();
        int currentPage = pageNo;
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sortByPrice", sortByPrice);
        model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
        return "homePage/index";
    }

    @GetMapping("/categoryId")
    public String searchByCategoryId(@RequestParam(name="categoryId") Integer categoryId, Model model) {
        List<Product> products = null;
        if(categoryId == 0) {
//          products = productService.getAllProducts();
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
    @GetMapping("/wishlist")
    public String viewWishList(Model model) {
        model.addAttribute("fragmentContent", "homePage/fragments/wishlist :: wishlist");
        return "homePage/index";
    }

    // Hiển thị 10 sản phẩm bán chạy nhất (feature products)
    @GetMapping("/feature-products")
    public String getFeatureProducts(Model model) {
        List<ProductDTO> featureProducts = productService.getTop10BestSellingProducts();
        model.addAttribute("featureProducts", featureProducts);
        model.addAttribute("fragmentContent", "homePage/fragments/featureProducts :: featureProducts");
        return "homePage/index";
    }

    // Trang sản phẩm tương tự (bán chạy, phân trang)
    @GetMapping("/similar-products")
    public String getSimilarProducts(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        int pageSize = 8;
        var page = productService.getAllBestSellingProducts(pageNo, pageSize);
        model.addAttribute("products", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("fragmentContent", "homePage/fragments/similarProducts :: similarProducts");
        return "homePage/index";
    }
}

package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.service.user.UserService;
import org.example.onlinesupermarket.service.cart.CartService;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Cart;
import org.example.onlinesupermarket.dto.product.ProductDTO;
import org.example.onlinesupermarket.dto.product.ProductDetailDTO;
import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.entity.WishList;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.service.category.CategoryService;
import org.example.onlinesupermarket.service.product.ProductService;
import org.example.onlinesupermarket.service.wishList.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WishListService wishListService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @GetMapping()
    public String homePage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<ProductDTO> featureProducts = productService.getTop10BestSellingProducts();
        List<ProductDTO> newestProducts = productService.getTop10NewestProducts();
        model.addAttribute("fragmentContent", "homePage/fragments/homePage :: homePage");
        model.addAttribute("categories", categories);
        model.addAttribute("featureProducts", featureProducts);
        model.addAttribute("newestProducts", newestProducts);
        return "homePage/index";
    }

    //truy cap home page
    @GetMapping("/listproduct")
    public String getList(
            Model model,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortType", required = false, defaultValue = "newest") String sortType,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo
    ) {
        int pageSize = 8;
        Page<ProductDTO> page = productService.filterAllProducts(name, categoryId, minPrice, maxPrice, sortType, pageNo, pageSize);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categories);
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sortType", sortType);
        model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
        return "homePage/index";
    }

    @GetMapping("/categoryId")
    public String searchByCategoryId(@RequestParam(name = "categoryId") Integer categoryId, Model model) {
        List<Product> products = null;
        if (categoryId == 0) {
//          products = productService.getAllProducts();
        } else {
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
        if (detail == null || detail.getProductId() == null) {
            model.addAttribute("error", "Không tìm thấy sản phẩm hoặc sản phẩm lỗi dữ liệu.");
            return "homePage/error";
        }
        Category category = categoryService.findByName(detail.getCategoryName());
        Integer categoryId = category.getCategoryId();
        Integer userId = 1;
        User user = userRepository.findByUserId(userId);
        boolean isInWishlist = wishListService.isProductInWishList(user, productService.getProductById(id));
        // Xử lý trạng thái wishlist cho moreProduct
        List<ProductDTO> moreProductRaw = productService.MoreLikeThis(categoryId, id);
        List<ProductDTO> moreProduct = moreProductRaw.stream().map(p -> {
            boolean inWish = wishListService.isProductInWishList(user, productService.getProductById(p.getProductId()));
            p.setInWishlist(inWish);
            return p;
        }).collect(Collectors.toList());
        List<ProductDTO> featuredProducts = productService.getTopBestSellingProducts(8);
        model.addAttribute("isInWishlist", isInWishlist);
        model.addAttribute("moreProduct", moreProduct);
        model.addAttribute("detail", detail);
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("fragmentContent", "homePage/fragments/productdetail :: productdetail");
        return "homePage/index";
    }

    @GetMapping("/wishlist")
    public String viewWishList(Model model) {
        Integer userId = 1; // giả lập user id
        User user = userRepository.findByUserId(userId);
        List<WishList> wishLists = wishListService.getWishListByUser(user);
        model.addAttribute("wishLists", wishLists);
        model.addAttribute("fragmentContent", "homePage/fragments/wishlist :: wishlist");
        return "homePage/index";
    }

    @PostMapping("/wishlist/add/{productId}")
    public String addToWishList(@PathVariable Integer productId, RedirectAttributes redirectAttributes) {
        Integer userId = 1; // giả lập user id
        User user = userRepository.findByUserId(userId);
        Product product = productService.getProductById(productId);
        wishListService.addProductToWishList(user, product);
        return "redirect:/home/productdetail/" + productId;
    }

    @PostMapping("/wishlist/remove/{productId}")
    public String removeFromWishList(@PathVariable Integer productId, @RequestParam(value = "from", required = false) String from) {
        Integer userId = 1; // giả lập user id
        User user = userRepository.findByUserId(userId);
        Product product = productService.getProductById(productId);
        wishListService.removeProductFromWishList(user, product);
        if ("wishlist".equals(from)) {
            return "redirect:/home/wishlist";
        } else {
            return "redirect:/home/productdetail/" + productId;
        }
    }

    // Hiển thị 10 sản phẩm bán chạy nhất (feature products)
    @GetMapping("/feature-products")
    public String getFeatureProductsPage(
            Model model,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortType", required = false, defaultValue = "bestselling") String sortType,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo
    ) {
        int pageSize = 8;
        Page<ProductDTO> page = productService.filterBestSellingProducts(name, categoryId, minPrice, maxPrice, sortType, pageNo, pageSize);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categories);
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sortType", sortType);
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

    @GetMapping("/newest-products")
    public String getNewestProducts(
            Model model,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortType", required = false, defaultValue = "newest") String sortType,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo
    ) {
        int pageSize = 8;
        Page<ProductDTO> page = productService.filterNewestProducts(name, categoryId, minPrice, maxPrice, sortType, pageNo, pageSize);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categories);
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sortType", sortType);
        model.addAttribute("fragmentContent", "homePage/fragments/newestProducts :: newestProducts");
        return "homePage/index";
    }
}


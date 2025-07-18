package org.example.onlinesupermarket.service.product;

import org.example.onlinesupermarket.dto.product.ProductDTO;
import org.example.onlinesupermarket.dto.product.ProductDetailDTO;
import org.example.onlinesupermarket.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<Product> getAllProducts(int pageNo);
    List<Product> findByCategoryId(Integer categoryId);
    ProductDetailDTO getProductDetail(Integer productId);
    //san pham tuong tu
    List<ProductDTO> MoreLikeThis(Integer categoryId, Integer productId);
    //san pham noi bat
    List<ProductDTO> getTopBestSellingProducts(int topN);
    // tim kiem san pham theo ten ket hop săp xep
    List<Product> searchByName(String name, String sortByPrice);
    List<Product> filterByPrice(Double minPrice, Double maxPrice, String sortByPrice);
    List<Product> searchByNameAndPrice(String name, Double minPrice, Double maxPrice, String sortByPrice);
    List<Product> getAllProducts(String sortByPrice);
    Page<Product> searchProducts(String name, Integer categoryId, Double minPrice, Double maxPrice, String sortByPrice, int pageNo, int pageSize);
    // Lấy top 10 sản phẩm bán chạy nhất
    List<ProductDTO> getTop10BestSellingProducts();
    // Lấy tất cả sản phẩm bán chạy (phân trang)
    Page<ProductDTO> getAllBestSellingProducts(int pageNo, int pageSize);
}

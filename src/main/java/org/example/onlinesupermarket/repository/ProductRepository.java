package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //tim san pham theo categoriesId
    List<Product> findProductByCategoryCategoryId (Integer categoryId);
    //detail san pham
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.productId = :productId")
    Optional<Product> findWithImages(@Param("productId") Integer productId);
    //more like this
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.category.categoryId = :categoryId and p.productId != :productId")
    List<Product> moreProduct(@Param("categoryId") Integer categoryId, @Param("productId") Integer productId);
    // san pham ban chay nhat
    @Query("SELECT p FROM Product p JOIN OrderItem oi ON p.productId = oi.product.productId GROUP BY p.productId, p.category, p.createdAt, p.description, p.name,p.image, p.price, p.status, p.stockQuantity ORDER BY SUM(oi.quantity) DESC")
    List<Product> findTopBestSellingProducts(Pageable pageable);
    // Tìm kiếm sản phẩm theo tên (LIKE, không phân biệt hoa thường)
    List<Product> findByNameContainingIgnoreCase(String name);
    // Lọc sản phẩm theo khoảng giá
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    // Kết hợp tìm kiếm tên và filter giá
    List<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, Double minPrice, Double maxPrice);
    // Sắp xếp theo giá tăng dần
    List<Product> findAllByOrderByPriceAsc();
    // Sắp xếp theo giá giảm dần
    List<Product> findAllByOrderByPriceDesc();
    // Sắp xếp theo giá tăng dần, nếu cùng giá thì theo tên
    List<Product> findAllByOrderByPriceAscNameAsc();
    // Sắp xếp theo giá giảm dần, nếu cùng giá thì theo tên
    List<Product> findAllByOrderByPriceDescNameAsc();
    // Search tên + sắp xếp tăng dần
    List<Product> findByNameContainingIgnoreCaseOrderByPriceAsc(String name);
    List<Product> findByNameContainingIgnoreCaseOrderByPriceDesc(String name);
    // Search tên + sắp xếp tăng dần
    List<Product> findByNameContainingIgnoreCaseOrderByPriceAscNameAsc(String name);
    List<Product> findByNameContainingIgnoreCaseOrderByPriceDescNameAsc(String name);
    // Filter giá + sắp xếp tăng/giảm
    List<Product> findByPriceBetweenOrderByPriceAsc(Double minPrice, Double maxPrice);
    List<Product> findByPriceBetweenOrderByPriceDesc(Double minPrice, Double maxPrice);
    // Filter giá + sắp xếp tăng/giảm
    List<Product> findByPriceBetweenOrderByPriceAscNameAsc(Double minPrice, Double maxPrice);
    List<Product> findByPriceBetweenOrderByPriceDescNameAsc(Double minPrice, Double maxPrice);
    // Search + filter + sắp xếp tăng/giảm
    List<Product> findByNameContainingIgnoreCaseAndPriceBetweenOrderByPriceAsc(String name, Double minPrice, Double maxPrice);
    List<Product> findByNameContainingIgnoreCaseAndPriceBetweenOrderByPriceDesc(String name, Double minPrice, Double maxPrice);
    // Search + filter + sắp xếp tăng/giảm
    List<Product> findByNameContainingIgnoreCaseAndPriceBetweenOrderByPriceAscNameAsc(String name, Double minPrice, Double maxPrice);
    List<Product> findByNameContainingIgnoreCaseAndPriceBetweenOrderByPriceDescNameAsc(String name, Double minPrice, Double maxPrice);
    Page<Product> findByNameContainingIgnoreCaseAndCategoryCategoryIdAndPriceBetween(String name, Integer categoryId, Double minPrice, Double maxPrice, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndCategoryCategoryId(String name, Integer categoryId, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, Double minPrice, Double maxPrice, Pageable pageable);
    Page<Product> findByCategoryCategoryIdAndPriceBetween(Integer categoryId, Double minPrice, Double maxPrice, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByCategoryCategoryId(Integer categoryId, Pageable pageable);
    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
    // JPQL lấy top 10 sản phẩm bán chạy nhất (bao gồm cả sản phẩm chưa bán)
    @Query("SELECT p, COALESCE(SUM(oi.quantity), 0) as totalSold FROM Product p LEFT JOIN p.orderItems oi GROUP BY p ORDER BY totalSold DESC")
    List<Object[]> findTop10BestSellingProducts(Pageable pageable);

    // JPQL lấy tất cả sản phẩm bán chạy (phân trang)
    @Query("SELECT p, COALESCE(SUM(oi.quantity), 0) as totalSold FROM Product p LEFT JOIN p.orderItems oi GROUP BY p ORDER BY totalSold DESC")
    Page<Object[]> findAllBestSellingProducts(Pageable pageable);
}

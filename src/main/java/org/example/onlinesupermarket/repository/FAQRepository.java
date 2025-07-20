package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Integer> {

    @Query("SELECT f FROM FAQ f WHERE f.active = true ORDER BY f.sortOrder ASC")
    List<FAQ> findActiveOrderBySortOrder();

    @Query("SELECT f FROM FAQ f ORDER BY f.sortOrder ASC, f.createdAt DESC")
    List<FAQ> findAllOrderBySortOrder();

    @Query("SELECT f FROM FAQ f WHERE f.active = true AND f.category = :category ORDER BY f.sortOrder ASC")
    List<FAQ> findActiveByCategoryOrderBySortOrder(@Param("category") String category);

    @Query("SELECT DISTINCT f.category FROM FAQ f WHERE f.active = true ORDER BY f.category")
    List<String> findAllActiveCategories();

    @Query("SELECT f FROM FAQ f WHERE f.active = true AND (f.question LIKE %:keyword% OR f.answer LIKE %:keyword%) ORDER BY f.sortOrder ASC")
    List<FAQ> searchActiveFAQs(@Param("keyword") String keyword);
}
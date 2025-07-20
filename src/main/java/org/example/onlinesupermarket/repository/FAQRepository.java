package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.FAQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Integer> {

    @Query("SELECT f FROM FAQ f WHERE " +
            "(:question IS NULL OR f.question LIKE %:question%) AND " +
            "(:active IS NULL OR f.active = :active) " +
            "ORDER BY f.sortOrder ASC, f.createdAt DESC")
    Page<FAQ> findWithFiltersAndPagination(@Param("question") String question,
                                           @Param("active") Boolean active,
                                           Pageable pageable);

    @Query("SELECT DISTINCT f.category FROM FAQ f WHERE f.active = true ORDER BY f.category")
    List<String> findAllActiveCategories();
}
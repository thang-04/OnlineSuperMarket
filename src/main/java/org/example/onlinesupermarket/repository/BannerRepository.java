package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    @Query("SELECT b FROM Banner b WHERE b.active = true ORDER BY b.sortOrder ASC")
    List<Banner> findActiveOrderBySortOrder();

    @Query("SELECT b FROM Banner b ORDER BY b.sortOrder ASC, b.createdAt DESC")
    List<Banner> findAllOrderBySortOrder();

    List<Banner> findByActiveOrderBySortOrder(boolean active);

    @Query("SELECT b FROM Banner b WHERE " +
            "(:title IS NULL OR b.title LIKE %:title%) AND " +
            "(:active IS NULL OR b.active = :active)")
    Page<Banner> findWithFilters(@Param("title") String title,
                                 @Param("active") Boolean active,
                                 Pageable pageable);
}
package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    @Query("SELECT b FROM Banner b WHERE b.active = true ORDER BY b.sortOrder ASC")
    List<Banner> findActiveOrderBySortOrder();

    @Query("SELECT b FROM Banner b ORDER BY b.sortOrder ASC, b.createdAt DESC")
    List<Banner> findAllOrderBySortOrder();

    List<Banner> findByActiveOrderBySortOrder(boolean active);
}
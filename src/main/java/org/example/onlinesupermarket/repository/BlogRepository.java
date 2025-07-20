package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    List<Blog> findAllByOrderByCreatedAtDesc();
    List<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    @Query("SELECT b FROM Blog b WHERE " +
            "(:title IS NULL OR b.title LIKE %:title%) AND " +
            "(:published IS NULL OR b.published = :published) " +
            "ORDER BY b.createdAt DESC")
    Page<Blog> findWithFilters(@Param("title") String title,
                               @Param("published") Boolean published,
                               Pageable pageable);
}
package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

} 
package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
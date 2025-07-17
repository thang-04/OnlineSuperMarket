package org.example.onlinesupermarket.service.category;

import org.example.onlinesupermarket.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category findByName(String name);
}
package org.example.onlinesupermarket.service.category;

import org.example.onlinesupermarket.dto.category.CategoryDTO;
import org.example.onlinesupermarket.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category findByName(String name);
    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Integer id);

    List<CategoryDTO> getCategoriesByNameContain(String keyword);

}
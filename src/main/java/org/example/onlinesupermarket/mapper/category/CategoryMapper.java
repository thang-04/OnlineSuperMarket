package org.example.onlinesupermarket.mapper.category;

import org.example.onlinesupermarket.dto.category.CategoryDTO;
import org.example.onlinesupermarket.entity.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        return new CategoryDTO(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) return null;
        return new Category(
                categoryDTO.getCategoryId(),
                categoryDTO.getName(),
                null, categoryDTO.getDescription()
        );
    }

    public static void updateEntity(CategoryDTO categoryDTO, Category existingCategory) {
        if (categoryDTO == null || existingCategory == null) {
            return;
        }

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setDescription(categoryDTO.getDescription());
    }
}

package org.example.onlinesupermarket.service.category.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.onlinesupermarket.dto.category.CategoryDTO;
import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.mapper.category.CategoryMapper;
import org.example.onlinesupermarket.repository.CategoryRepository;
import org.example.onlinesupermarket.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        category.setCategoryId(null);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        return CategoryMapper.toDTO(categoryRepository.findById(id).get());
    }

    @Override
    public List<CategoryDTO> getCategoriesByNameContain(String keyword) {
        return List.of();
    }

}
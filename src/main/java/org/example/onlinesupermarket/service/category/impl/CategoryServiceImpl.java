package org.example.onlinesupermarket.service.category.impl;

import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.repository.CategoryRepository;
import org.example.onlinesupermarket.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
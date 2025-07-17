package org.example.onlinesupermarket.service.product.impl;

import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.repository.ProductRepository;
import org.example.onlinesupermarket.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productRepository.findProductByCategoryCategoryId(categoryId);
    }
}

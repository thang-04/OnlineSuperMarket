package org.example.onlinesupermarket.service.product;

import org.example.onlinesupermarket.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> findByCategoryId(Integer categoryId);
}

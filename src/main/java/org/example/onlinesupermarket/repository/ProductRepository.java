package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //tim san pham theo categoriesId
    List<Product> findProductByCategoryCategoryId (Integer categoryId);
}

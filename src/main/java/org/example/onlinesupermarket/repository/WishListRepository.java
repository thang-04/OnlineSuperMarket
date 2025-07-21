package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.WishList;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findByUser(User user);
    List<WishList> findByProduct(Product product);
    WishList findByUserAndProduct(User user, Product product);
} 
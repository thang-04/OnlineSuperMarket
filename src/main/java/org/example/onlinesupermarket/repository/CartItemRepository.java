package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Cart;
import org.example.onlinesupermarket.entity.CartItem;
import org.example.onlinesupermarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    List<CartItem> findByCartAndProduct(Cart cart, Product product);
} 
package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Cart;
import org.example.onlinesupermarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
} 
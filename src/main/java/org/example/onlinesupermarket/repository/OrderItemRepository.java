package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
} 
package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserUserId(Integer userId);
    Long countByStatus(String status);

    List<Order> findTop5ByOrderByOrderDateDesc();

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'COMPLETED' AND o.orderDate >= :startOfDay")
    Double findTodayIncome(LocalDateTime startOfDay);
}

package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.dto.order.MonthlyIncome;
import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserUserId(Integer userId);
    Long countByStatus(String status);
    List<Order> findByUser(User user);
    List<Order> findTop5ByOrderByOrderDateDesc();

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'COMPLETED' AND o.orderDate >= :startOfDay")
    Double findTodayIncome(LocalDateTime startOfDay);

    @Query("SELECT new org.example.onlinesupermarket.dto.order.MonthlyIncome(MONTH(o.orderDate), SUM(o.totalAmount)) " +
            "FROM Order o " +
            "WHERE YEAR(o.orderDate) = :year AND o.status = 'Completed' " +
            "GROUP BY MONTH(o.orderDate) " +
            "ORDER BY MONTH(o.orderDate) ASC")
    List<MonthlyIncome> findMonthlyIncomeForYear(@Param("year") int year);

}


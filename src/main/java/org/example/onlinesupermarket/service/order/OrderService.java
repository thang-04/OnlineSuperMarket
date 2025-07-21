package org.example.onlinesupermarket.service.order;

import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Map;

@Service
public interface OrderService {
    Order createOrder(User user, Integer addressId, String couponCode);
    List<Order> getOrdersByUser(User user);
    Order getOrderById(Integer orderId);
    void updateOrderStatus(Integer orderId, String status);
    Double calculateOrderTotal(Order order);

    Map<String, Object> getDashboardStatistics();

    List<Double> getMonthlyIncomeForYear(int year);
}

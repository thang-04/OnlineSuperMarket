package org.example.onlinesupermarket.service.order;

import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, Integer addressId, String couponCode);
    List<Order> getOrdersByUser(User user);
    Order getOrderById(Long orderId);
    void updateOrderStatus(Integer orderId, String status);
    Double calculateOrderTotal(Order order);
}

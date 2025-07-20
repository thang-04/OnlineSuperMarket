package org.example.onlinesupermarket.service.order.impl;

import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.repository.OrderRepository;
import org.example.onlinesupermarket.mapper.order.OrderMapper;
import org.example.onlinesupermarket.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUserUserId(userId);
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("pendingOrders", orderRepository.countByStatus("PENDING"));
        stats.put("cancelledOrders", orderRepository.countByStatus("CANCELLED"));
        stats.put("processingOrders", orderRepository.countByStatus("PROCESSING"));
        stats.put("recentOrders", orderRepository.findTop5ByOrderByOrderDateDesc());

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Double todayIncome = orderRepository.findTodayIncome(startOfDay);
        stats.put("todayIncome", todayIncome != null ? todayIncome : 0.0);
        return stats;
    }
}

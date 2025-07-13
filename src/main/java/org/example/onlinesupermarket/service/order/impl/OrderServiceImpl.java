package org.example.onlinesupermarket.service.order.impl;

import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.repository.OrderRepository;
import org.example.onlinesupermarket.mapper.order.OrderMapper;
import org.example.onlinesupermarket.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
}

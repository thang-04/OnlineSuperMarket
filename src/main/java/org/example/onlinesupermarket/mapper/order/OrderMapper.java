package org.example.onlinesupermarket.mapper.order;

import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.mapper.address.AddressMapper;
import org.example.onlinesupermarket.dto.order.OrderItemDTO;
import org.example.onlinesupermarket.entity.OrderItem;
import org.example.onlinesupermarket.mapper.order.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    public OrderDTO toDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCouponCode(order.getCouponCode());
        dto.setAddress(addressMapper.toDTO(order.getAddress()));
        List<OrderItemDTO> orderItems = order.getOrderItems() != null ?
            order.getOrderItems().stream().map(orderItemMapper::toDTO).collect(Collectors.toList()) : null;
        dto.setOrderItems(orderItems);
        // Tính tổng tiền thực tế từ orderItems
        double calculatedTotal = 0.0;
        if (orderItems != null) {
            calculatedTotal = orderItems.stream()
                .mapToDouble(i -> (i.getUnitPrice() != null ? i.getUnitPrice() : 0.0) * i.getQuantity())
                .sum();
        }
        dto.setCalculatedTotal(calculatedTotal);
        return dto;
    }
} 
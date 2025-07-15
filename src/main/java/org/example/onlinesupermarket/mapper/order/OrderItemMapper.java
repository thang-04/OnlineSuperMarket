package org.example.onlinesupermarket.mapper.order;

import org.example.onlinesupermarket.entity.OrderItem;
import org.example.onlinesupermarket.dto.order.OrderItemDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) return null;
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setProductId(orderItem.getProduct().getProductId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }
}

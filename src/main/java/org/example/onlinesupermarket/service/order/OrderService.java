package org.example.onlinesupermarket.service.order;

import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    List<OrderDTO> getOrdersByUserId(Integer userId);
}

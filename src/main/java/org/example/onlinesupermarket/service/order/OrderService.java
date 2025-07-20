package org.example.onlinesupermarket.service.order;

import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    List<OrderDTO> getOrdersByUserId(Integer userId);

    Map<String, Object> getDashboardStatistics();
}

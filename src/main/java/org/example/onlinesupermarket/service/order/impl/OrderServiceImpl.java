package org.example.onlinesupermarket.service.order.impl;

import org.example.onlinesupermarket.dto.order.MonthlyIncome;
import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.example.onlinesupermarket.entity.*;
import org.example.onlinesupermarket.repository.*;
import org.example.onlinesupermarket.service.order.OrderService;
import org.example.onlinesupermarket.service.cart.CartService;
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
    private OrderRepository orderRepo;
    @Autowired
    private AddressRepository addressRepo;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemRepository cartItemRepo;
    @Autowired
    private ProductRepository productRepo;

    @Override
    public Order createOrder(User user, Integer addressId, String couponCode) {
        Cart cart = cartService.getCartByUser(user);
        List<CartItem> cartItems = cartItemRepo.findByCart(cart);

        Order order = new Order();
        order.setUser(user);
        order.setAddress(addressRepo.findById(addressId).orElseThrow());
        order.setCouponCode(couponCode);
        order.setStatus("Pending");
        order.setOrderDate(java.time.LocalDateTime.now());

        double total = 0;
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setUnitPrice(ci.getProduct().getPrice());
            oi.setQuantity(ci.getQuantity());
            order.getOrderItems().add(oi);
            total += ci.getProduct().getPrice() * ci.getQuantity();
        }
        order.setTotalAmount(total);

        orderRepo.save(order);
        cartItemRepo.deleteAll(cartItems);

        return order;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepo.findByUser(user);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepo.findById(orderId).orElseThrow();
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepo.save(order);
    }

    @Override
    public Double calculateOrderTotal(Order order) {
        if (order.getOrderItems() == null) return 0.0;
        return order.getOrderItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }


    @Override
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("pendingOrders", orderRepo.countByStatus("PENDING"));
        stats.put("cancelledOrders", orderRepo.countByStatus("CANCELLED"));
        stats.put("processingOrders", orderRepo.countByStatus("PROCESSING"));
        stats.put("recentOrders", orderRepo.findTop5ByOrderByOrderDateDesc());

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Double todayIncome = orderRepo.findTodayIncome(startOfDay);
        stats.put("todayIncome", todayIncome != null ? todayIncome : 0.0);
        return stats;
    }

    @Override
    public List<Double> getMonthlyIncomeForYear(int year) {
        List<MonthlyIncome> monthlyIncomes = orderRepo.findMonthlyIncomeForYear(year);

        Double[] yearlyIncome = new Double[12];
        for (int i = 0; i < 12; i++) {
            yearlyIncome[i] = 0.0;
        }

        for (MonthlyIncome income : monthlyIncomes) {
            int monthIndex = income.getMonth() - 1;
            if (monthIndex >= 0 && monthIndex < 12) {
                yearlyIncome[monthIndex] = income.getTotal();
            }
        }

        return List.of(yearlyIncome);
    }
}

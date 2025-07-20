package org.example.onlinesupermarket.service.order.impl;

import org.example.onlinesupermarket.entity.*;
import org.example.onlinesupermarket.repository.*;
import org.example.onlinesupermarket.service.order.OrderService;
import org.example.onlinesupermarket.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Order getOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow();
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepo.findById(orderId.longValue()).orElseThrow();
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
}

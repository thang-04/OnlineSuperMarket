package org.example.onlinesupermarket.service.cart.impl;

import org.example.onlinesupermarket.entity.*;
import org.example.onlinesupermarket.repository.*;
import org.example.onlinesupermarket.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CartItemRepository cartItemRepo;
    @Autowired
    private ProductRepository productRepo;

    @Override
    public Cart getCartByUser(User user) {
        return cartRepo.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
            return cartRepo.save(cart);
        });
    }

    @Override
    public void addItem(User user, Integer productId, int quantity) {
        Cart cart = getCartByUser(user);
        Product product = productRepo.findById(productId).orElseThrow();
        List<CartItem> items = cartItemRepo.findByCartAndProduct(cart, product);
        CartItem item;
        if (items.isEmpty()) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
        } else {
            item = items.get(0);
            item.setQuantity(item.getQuantity() + quantity);
            // Xóa các bản ghi thừa nếu có
            for (int i = 1; i < items.size(); i++) {
                cartItemRepo.delete(items.get(i));
            }
        }
        cartItemRepo.save(item);
    }

    @Override
    public void updateItem(User user, Integer productId, int quantity) {
        Cart cart = getCartByUser(user);
        Product product = productRepo.findById(productId).orElseThrow();
        List<CartItem> items = cartItemRepo.findByCartAndProduct(cart, product);
        if (!items.isEmpty()) {
            CartItem item = items.get(0);
            item.setQuantity(quantity);
            cartItemRepo.save(item);
            // Xóa các bản ghi thừa nếu có
            for (int i = 1; i < items.size(); i++) {
                cartItemRepo.delete(items.get(i));
            }
        }
    }

    @Override
    public void removeItem(User user, Integer productId) {
        Cart cart = getCartByUser(user);
        Product product = productRepo.findById(productId).orElseThrow();
        List<CartItem> items = cartItemRepo.findByCartAndProduct(cart, product);
        for (CartItem item : items) {
            cartItemRepo.delete(item);
        }
    }
}

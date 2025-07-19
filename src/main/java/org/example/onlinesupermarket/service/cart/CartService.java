package org.example.onlinesupermarket.service.cart;

import org.example.onlinesupermarket.entity.Cart;
import org.example.onlinesupermarket.entity.User;

public interface CartService {
    Cart getCartByUser(User user);
    void addItem(User user, Integer productId, int quantity);
    void updateItem(User user, Integer productId, int quantity);
    void removeItem(User user, Integer productId);
}

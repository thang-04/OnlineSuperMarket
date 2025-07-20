package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Cart;
import org.example.onlinesupermarket.service.cart.CartService;
import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/home/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        model.addAttribute("fragmentContent", "homePage/fragments/cartContent :: cartContent");
        return "homePage/index";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer productId, @RequestParam int quantity, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        cartService.addItem(user, productId, quantity);
        return "redirect:/home";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Integer productId, @RequestParam int quantity, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        cartService.updateItem(user, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Integer productId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        cartService.removeItem(user, productId);
        return "redirect:/cart";
    }
} 
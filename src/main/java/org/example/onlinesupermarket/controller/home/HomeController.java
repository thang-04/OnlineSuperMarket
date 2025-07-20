package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.service.user.UserService;
import org.example.onlinesupermarket.service.cart.CartService;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping()
    public String getHomePage(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            Cart cart = cartService.getCartByUser(user);
            model.addAttribute("cart", cart);
        }
        model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
        return "homePage/index";
    }

}

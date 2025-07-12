package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.dto.order.OrderDTO;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.security.CustomUserDetails;
import org.example.onlinesupermarket.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.example.onlinesupermarket.repository.OrderRepository;

@Controller
@RequestMapping("/home/my-order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public String showOrderPage(Model model) {
        User currentUser = getCurrentUser();
        List<OrderDTO> orders = orderService.getOrdersByUserId(currentUser.getUserId());
        model.addAttribute("ListOrders", orders);
        model.addAttribute("fragmentContent", "homePage/fragments/orderContent :: orderContent");
        return "homePage/index";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUserEntity();
        }
        throw new RuntimeException("User not authenticated");
    }
}

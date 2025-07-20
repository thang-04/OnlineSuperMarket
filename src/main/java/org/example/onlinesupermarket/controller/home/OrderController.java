package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.service.order.OrderService;
import org.example.onlinesupermarket.service.user.UserService;
import org.example.onlinesupermarket.repository.AddressRepository;
import org.example.onlinesupermarket.service.cart.CartService;
import org.example.onlinesupermarket.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepo;
    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkoutForm(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("addresses", addressRepo.findByUser(user));
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        model.addAttribute("fragmentContent", "homePage/fragments/checkoutContent :: checkoutContent");
        return "homePage/index";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam Integer addressId,
                                  @RequestParam String paymentMethod,
                                  @RequestParam(required = false) String couponCode,
                                  Principal principal,
                                  Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByEmail(principal.getName());
        try {
            if ("VNPAY".equalsIgnoreCase(paymentMethod)) {
                // Lưu thông tin vào session để sử dụng sau khi thanh toán thành công
                request.getSession().setAttribute("addressId", addressId);
                request.getSession().setAttribute("couponCode", couponCode);
                // Chuyển sang trang thanh toán VNPAY (PaymentController sẽ lấy thông tin từ session)
                return "redirect:/payment/vnpay";
            } else {
                // COD: tạo order ngay
                Order order = orderService.createOrder(user, addressId, couponCode);
                redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công!");
                return "redirect:/home/orders";
            }
        } catch (Exception e) {
            // On error, reload checkout page with error message
            model.addAttribute("error", "Đã xảy ra lỗi khi đặt hàng: " + e.getMessage());
            model.addAttribute("addresses", addressRepo.findByUser(user));
            Cart cart = cartService.getCartByUser(user);
            model.addAttribute("cart", cart);
            model.addAttribute("fragmentContent", "homePage/fragments/checkoutContent :: checkoutContent");
            return "homePage/index";
        }
    }


    @GetMapping
    public String listOrders(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Order> orders = orderService.getOrdersByUser(user);
        Map<Integer, Double> orderTotals = new HashMap<>();
        for (Order order : orders) {
            orderTotals.put(order.getOrderId(), orderService.calculateOrderTotal(order));
        }
        model.addAttribute("ListOrders", orders);
        model.addAttribute("orderTotals", orderTotals);
        model.addAttribute("fragmentContent", "homePage/fragments/orderContent :: orderContent");
        return "homePage/index";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order_detail";
    }
}

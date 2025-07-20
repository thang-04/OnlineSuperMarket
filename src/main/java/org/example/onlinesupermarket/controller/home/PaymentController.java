package org.example.onlinesupermarket.controller.home;

import org.example.onlinesupermarket.entity.Order;
import org.example.onlinesupermarket.service.order.OrderService;
import org.example.onlinesupermarket.service.vnpay.vnpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.service.user.UserService;
import org.example.onlinesupermarket.service.cart.CartService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private vnpayService vnpayService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/vnpay")
    public String vnpayPayment(HttpServletRequest request, Principal principal) {
        // Lấy user và cart, tính amount
        User user = userService.findByEmail(principal.getName());
        org.example.onlinesupermarket.entity.Cart cart = cartService.getCartByUser(user);
        double totalAmount = (cart != null) ? cart.getTotalAmount() : 0;
        int amount = (int) (totalAmount * 100);
        request.setAttribute("amount", amount);
        String urlReturn = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath() + "/payment");
        request.setAttribute("urlReturn", urlReturn);
        try {
            String paymentUrl = vnpayService.createOrder(request);
            return "redirect:" + paymentUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    // Endpoint to handle VNPay return URL
    @GetMapping("/vnpay-payment-return")
    public String vnpayReturn(HttpServletRequest request, Model model, Principal principal) {
        int result = vnpayService.orderReturn(request);
        if (result == 1) {
            // Thanh toán thành công, tạo order và xóa cart
            HttpSession session = request.getSession();
            Integer addressId = (Integer) session.getAttribute("addressId");
            String couponCode = (String) session.getAttribute("couponCode");
            User user = userService.findByEmail(principal.getName());
            if (user != null && addressId != null) {
                orderService.createOrder(user, addressId, couponCode);
                // Xóa thông tin session sau khi tạo order
                session.removeAttribute("addressId");
                session.removeAttribute("couponCode");
            }
            model.addAttribute("result", "Thanh toán thành công!");
        } else if (result == 0) {
            // Thanh toán thất bại hoặc bị hủy, không tạo order
            model.addAttribute("result", "Thanh toán thất bại hoặc bị hủy!");
        } else {
            model.addAttribute("result", "Xác thực chữ ký không hợp lệ!");
        }
        String responseCode = request.getParameter("vnp_ResponseCode");
        model.addAttribute("responseCode", responseCode);
        model.addAttribute("fragmentContent", "homePage/fragments/vnpayResult :: vnpayResult");
        return "homePage/index";
    }
} 
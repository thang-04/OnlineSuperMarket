package org.example.onlinesupermarket.controller.dashBoard;

import org.example.onlinesupermarket.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class DashBoardController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String getdashBoardPage(Model model) {
        Map<String, Object> statistics = orderService.getDashboardStatistics();

        model.addAttribute("pendingOrders", statistics.get("pendingOrders"));
        model.addAttribute("cancelledOrders", statistics.get("cancelledOrders"));
        model.addAttribute("processingOrders", statistics.get("processingOrders"));
        model.addAttribute("todayIncome", statistics.get("todayIncome"));
        model.addAttribute("recentOrders", statistics.get("recentOrders"));

        List<Double> monthlyIncome = orderService.getMonthlyIncomeForYear(2025);
        List<String> months = List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        model.addAttribute("monthlyIncomeData", monthlyIncome);
        model.addAttribute("monthsData", months);


        return "dashBoard/index";
    }
}
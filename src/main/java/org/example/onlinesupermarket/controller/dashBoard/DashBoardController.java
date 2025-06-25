package org.example.onlinesupermarket.controller.dashBoard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {

    @GetMapping()
    public String getdashBoardPage(Model model) {
        model.addAttribute("fragmentContent", "dashBoard/fragments/contentMain :: contentMain");
        return "dashBoard/index";
    }
}

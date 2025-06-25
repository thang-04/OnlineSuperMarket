//package org.example.onlinesupermarket.controller.login;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class LoginController {
//
//        @GetMapping("/login")
//        public String loginPage(@RequestParam(value = "error", required = false) String error,
//                              @RequestParam(value = "logout", required = false) String logout,
//                              @RequestParam(value = "expired", required = false) String expired,
//                              Model model) {
//
//            if (error != null) {
//                model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
//            }
//
//            if (logout != null) {
//                model.addAttribute("successMessage", "Đăng xuất thành công!");
//            }
//
//            if (expired != null) {
//                model.addAttribute("warningMessage", "Phiên đăng nhập đã hết hạn!");
//            }
//            model.addAttribute("fragmentContent", "homePage/fragments/contentMain :: contentMain");
//            return "homePage/index";
//        }
//
//        @GetMapping("/403")
//        public String showAccessDenied() {
//            return "error/403";
//        }
//
//        @GetMapping("/logout")
//        public String logout() {
//            return "redirect:/login?logout=true";
//        }
//
//
//    }
//

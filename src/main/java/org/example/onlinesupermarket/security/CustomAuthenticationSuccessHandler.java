//package org.example.onlinesupermarket.security;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//
//import java.io.IOException;
//
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final RequestCache requestCache = new HttpSessionRequestCache();
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        //check last url
//        var savedRequest = requestCache.getRequest(request, response);
//
//        if (savedRequest != null) {
//            String targetUrl = savedRequest.getRedirectUrl();
//            //check role
//            if (hasRole(authentication, "ROLE_ADMIN") || !targetUrl.contains("/dashboard")) {
//                response.sendRedirect(targetUrl);
//                return;
//            } else {
//                response.sendRedirect(request.getContextPath() + "/403");
//                return;
//            }
//        }
//
//        // Không có URL trước đó -> redirect theo vai trò
//        String redirectURL = request.getContextPath();
//        if (hasRole(authentication, "ROLE_ADMIN")) {
//            redirectURL += "/dashboard";
//        } else {
//            redirectURL += "/home";
//        }
//        response.sendRedirect(redirectURL);
//    }
//
//    private boolean hasRole(Authentication auth, String roleName) {
//        for (GrantedAuthority authority : auth.getAuthorities()) {
//            if (authority.getAuthority().equals(roleName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}

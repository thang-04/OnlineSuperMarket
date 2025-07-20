package org.example.onlinesupermarket.security;

import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.repository.RoleRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        String fullName = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            // Nếu có avatar Google, cập nhật
            if (picture != null && (user.getUserImg() == null || user.getUserImg().contains("googleusercontent"))) {
                user.setUserImg(picture);
                userRepository.save(user);
            }
        } else {
            user = new User();
            user.setEmail(email);
            user.setFullName(fullName);
            user.setUserImg(picture);
            // Gán role mặc định là USER
            Role roleUser = roleRepository.findByRoleName("USER").orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
            user.setRole(roleUser);
            // Đặt password mặc định đã mã hóa
            user.setPassword(passwordEncoder.encode("default password"));
            userRepository.save(user);
            // Đánh dấu là user mới đăng nhập Google
            var requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                requestAttributes.setAttribute("OAUTH2_NEW_USER", true, RequestAttributes.SCOPE_SESSION);
            }
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().toUpperCase());
        return new CustomUserDetails(user, Collections.singleton(authority), oauth2User.getAttributes());
    }
}
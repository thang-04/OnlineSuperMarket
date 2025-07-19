//package org.example.onlinesupermarket.security;
//
//import org.example.onlinesupermarket.entity.User;
//import org.example.onlinesupermarket.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> optuser = userRepository.findByEmail(email);
//        if (optuser.isEmpty()) {
//            System.out.println("User not found: " + email);
//            throw new UsernameNotFoundException("User not found: " + email);
//        }
//        User user = optuser.get();
//        System.out.println("Found user: " + user.getFullName() + ", RoleID: " + user.getRole().getRoleId());
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().toUpperCase()));
//
//        //custom user detail
//        return new CustomUserDetails(user, authorities);
//    }
//}
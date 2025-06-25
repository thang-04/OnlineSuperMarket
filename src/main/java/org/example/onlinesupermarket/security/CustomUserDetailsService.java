//package org.example.onlinesupermarket.security;
//
//import org.example.hotelmanagement.entity.Guest;
//import org.example.hotelmanagement.repository.GuestRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Deque;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private GuestRepository guestRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Guest> optGuest = guestRepository.findByEmail(email);
//        if (optGuest.isEmpty()) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//
//        Guest guest = optGuest.get();
//        if (guest.getRole() == null) {
//            throw new UsernameNotFoundException("User has no role assigned: " + email);
//        }
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        String roleName = guest.getRole().getRoleName().toUpperCase();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
//
//        return new CustomUserDetails(guest, authorities);
//    }
//}
//

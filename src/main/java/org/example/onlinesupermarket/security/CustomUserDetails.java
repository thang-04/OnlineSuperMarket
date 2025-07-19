//package org.example.onlinesupermarket.security;
//
//import lombok.Getter;
//import org.example.onlinesupermarket.entity.Address;
//import org.example.onlinesupermarket.entity.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.Map;
//
//@Getter
//public class CustomUserDetails implements UserDetails, OAuth2User {
//    private final User user;
//    private final Collection<? extends GrantedAuthority> authorities;
//    private Map<String, Object> attributes; // chứa info từ Google
//
//    // Constructor cho form login
//    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
//        this.user = user;
//        this.authorities = authorities;
//    }
//
//    // Constructor cho OAuth2 login
//    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
//        this.user = user;
//        this.authorities = authorities;
//        this.attributes = attributes;
//    }
//
//    public String getFullName() {
//        return user.getFullName() != null ? user.getFullName() : "";
//    }
//
//    public String getPhone() {
//        return user.getPhoneNumber();
//    }
//
//    public Address getAddress() {
//        return user.getAddresses() != null && !user.getAddresses().isEmpty() ? user.getAddresses().get(0) : null;
//    }
//    public String getEmail() {
//        return user.getEmail() != null ? user.getEmail() : "";
//    }
//
//    public String userImg() {return user.getUserImg();}
//
//    public Integer getUserId() {return user.getUserId();}
//
//    public User getUserEntity() {return this.user;}
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public String getName() {
//        return user.getEmail();
//    }
//}
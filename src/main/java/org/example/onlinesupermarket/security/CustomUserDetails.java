//package org.example.onlinesupermarket.security;
//
//import lombok.Getter;
//import org.example.hotelmanagement.entity.Guest;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.LocalDate;
//import java.util.Collection;
//
//@Getter
//public class CustomUserDetails implements UserDetails {
//    private final Guest guest;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public CustomUserDetails(Guest guest, Collection<? extends GrantedAuthority> authorities) {
//        this.guest = guest;
//        this.authorities = authorities;
//    }
//
//    public String getUserName() {
//        return guest.getUserName() != null ? guest.getUserName() : "";
//    }
//
//    public String getPhone() {
//        return guest.getPhone() != null ? guest.getPhone() : "";
//    }
//
//    public LocalDate getBirthday() {
//        return guest.getDateOfBirth();
//    }
//
//    public String getAddress() {
//        return guest.getAddress() != null ? guest.getAddress() : "";
//    }
//
//    public String getEmail() {
//        return guest.getEmail() != null ? guest.getEmail() : "";
//    }
//
//    public String getProfilePicture() {
//        return guest.getProfilePicture();
//    }
//
//    public int getGuestId() {
//        return guest.getGuestID();
//    }
//
//    public Guest getUserEntity() {
//        return this.guest;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return guest.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return guest.getEmail();
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
//}
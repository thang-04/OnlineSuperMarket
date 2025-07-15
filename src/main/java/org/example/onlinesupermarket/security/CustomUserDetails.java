package org.example.onlinesupermarket.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.onlinesupermarket.entity.Address;
import org.example.onlinesupermarket.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;
    public String getFullName() {
        return user.getFullName() != null ? user.getFullName() : "";
    }

    public String getPhone() {
        return user.getPhoneNumber();
    }

    public Address getAddress() {
        return user.getAddresses() != null && !user.getAddresses().isEmpty() ? user.getAddresses().get(0) : null;
    }
    public String getEmail() {
        return user.getEmail() != null ? user.getEmail() : "";
    }

    public String userImg() {return user.getUserImg();}

    public Integer getUserId() {return user.getUserId();}

    public User getUserEntity() {return this.user;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
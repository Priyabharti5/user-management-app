package com.priya.user_management_system.security;


import com.priya.user_management_system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Custom implementation of Spring Security's UserDetails.
 */
@AllArgsConstructor
@Getter
@Slf4j
public class AuthCustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.trace("Assigning default authority ROLE_USER for {}", user.getUsername());
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public Integer getUserId() {
        return user.getUserId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthCustomUserDetails that)) return false;
        return Objects.equals(user.getUserId(), that.user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getUserId());
    }

    @Override
    public String toString() {
        return "AuthCustomUserDetails{" +
                "username='" + user.getUsername() + '\'' +
                ", userId=" + user.getUserId() +
                ", email='" + user.getEmail() + '\'' +
                '}';
    }
}

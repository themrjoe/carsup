package com.el.opu.carsup.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;


public class JwtUser implements UserDetails {

    private final Long id;
    private final String userName;
    private final String password;
    private final String email;
    private final boolean enabled;
    private final Date lastPasswordResetedDate;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Long id,
            String userName,
            String password,
            String email,
            boolean enabled,
            Date lastPasswordResetedDate,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.authorities = authorities;
        this.lastPasswordResetedDate = lastPasswordResetedDate;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }


    public Date getLastPasswordResetedDate() {
        return lastPasswordResetedDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

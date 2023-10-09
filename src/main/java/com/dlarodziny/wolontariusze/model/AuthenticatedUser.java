package com.dlarodziny.wolontariusze.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
@ToString
public class AuthenticatedUser implements UserDetails {

    @Id
    private String username;
    private String password;
    private int enabled;
    private boolean active = true;
    private Set<GrantedAuthority> roles = new HashSet<>();

    public AuthenticatedUser() {}
    public AuthenticatedUser(Volunteer volunteer) {
        this.setUsername(volunteer.getUsername());
        this.setPassword(volunteer.getPassword());
        this.setEnabled(volunteer.isActive() ? 1 : 0);
        this.roles.add(new SimpleGrantedAuthority(volunteer.getRole()));

        log.info("Match found : " + this.toString());
    }

    @Builder
    public AuthenticatedUser(String username, String password){
        this.username = username;
        this.password = password;
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}

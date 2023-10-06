package com.dlarodziny.wolontariusze.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class AuthenticatedUser implements UserDetails {

    @Id
    private String username;
    private String password;

    private boolean active = true;
    private Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

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

//    old implementation
//    private String username;
//    private Collection authorities;
//
//    public AuthenticatedUser(String username, Collection authorities){
//        this.username = username;
//        this.authorities = authorities;
//    }
//
//    @Override
//    public Collection getAuthorities() {
//        return this.authorities;
//    }
//    @Override
//    public String getPassword() {
//        return getPassword();
//    }
//    @Override
//    public String getUsername() {
//        return username;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}

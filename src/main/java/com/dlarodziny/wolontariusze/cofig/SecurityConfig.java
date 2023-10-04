package com.dlarodziny.wolontariusze.cofig;

import com.dlarodziny.wolontariusze.repository.VolunteerRepo;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired
    VolunteerService volunteerService;

    @Autowired
    VolunteerRepo volunteerRepo;

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
               .username("admin")
               .password("admin")
               .roles("ADMIN")
               .build();
        // UserDetails user = User.withDefaultPasswordEncoder()
        //        .username("user")
        //        .password("user")
        //        .roles("USER")
        //        .build();
        // return new MapReactiveUserDetailsService(admin, user);

        var userList = volunteerRepo.findAll()
                .map(volunteer -> User.withDefaultPasswordEncoder()
                        .username(volunteer.getUsername())
                        .password(volunteer.getPassword())
                        .roles(volunteer.getRole())
                        .build())
                .collectList().block();
        assert userList != null;
        userList.add(admin);
        return new MapReactiveUserDetailsService(userList);
    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyExchange().authenticated()
                )
                .formLogin(withDefaults())
                .logout(withDefaults());
        ;
        return http.build();
    }
}

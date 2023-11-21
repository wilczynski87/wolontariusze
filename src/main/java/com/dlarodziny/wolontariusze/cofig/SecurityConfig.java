package com.dlarodziny.wolontariusze.cofig;

import com.dlarodziny.wolontariusze.repository.VolunteerRepo;
import com.dlarodziny.wolontariusze.service.AuthenticatedUserService;
import com.dlarodziny.wolontariusze.service.VolunteerService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired
    VolunteerService volunteerService;

    @Autowired
    VolunteerRepo volunteerRepo;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Bean
	protected ReactiveAuthenticationManager reactiveAuthenticationManager() {
		return authentication -> authenticatedUserService.findByUsername(authentication.getPrincipal().toString())
			.switchIfEmpty( Mono.error( new UsernameNotFoundException("User not found")))
			.flatMap(user -> {
				final String username = authentication.getPrincipal().toString();
				final CharSequence rawPassword = authentication.getCredentials().toString();

                if(rawPassword.equals(user.getPassword())){

					log.info("User has been authenticated {}", username);
					return Mono.just( new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities()) );
				}

				//This constructor can be safely used by any code that wishes to create a UsernamePasswordAuthenticationToken, as the isAuthenticated() will return false.
				return Mono.just( new UsernamePasswordAuthenticationToken(username, authentication.getCredentials()) );
			});
	}

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/saveVolunteers", "/saveVolunteerDetails").permitAll()
                        .pathMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyExchange().authenticated()
                )
                .formLogin(withDefaults())
                .logout(withDefaults())
                ;
        return http.build();
    }
}
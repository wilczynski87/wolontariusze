package com.dlarodziny.wolontariusze.cofig;

import com.dlarodziny.wolontariusze.repository.VolunteerRepo;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
        var userList = volunteerRepo.findAll()
                .map(volunteer -> User.withDefaultPasswordEncoder()
                        .username(volunteer.getUsername())
                        .password(volunteer.getPassword())
                        .roles(volunteer.getRole())
                        .build())
                .collectList().block();
//        return new MapReactiveUserDetailsService(admin, user);
        assert userList != null;
        return new MapReactiveUserDetailsService(userList);
    }
//    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        System.out.println("userDetailsService in use!");
//        return (username) -> volunteerService.findByUsername(username);
//    }

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
////                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/admin").hasAuthority("ROLE_ADMIN")
//                        .anyExchange().authenticated()
//                )
////                .httpBasic(withDefaults())
////                .formLogin(withDefaults())
//                .formLogin(formLogin -> formLogin
//                        .loginPage("redirect:/user")
//                )
//        ;
//        return http.build();
//    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyExchange().authenticated()
                )
//                .httpBasic(withDefaults())
                .formLogin(withDefaults())
//                .formLogin(formLogin -> formLogin.authenticationSuccessHandler((webFilterExchange, authentication) ->
//                        webFilterExchange.getExchange().transformUrl("/admin")))
                .logout(withDefaults());
        ;
        return http.build();
    }
//    @Bean
//    public WebFilterChainServerAuthenticationSuccessHandler authenticationSuccessHandler() {
//        WebFilterChainServerAuthenticationSuccessHandler successHandler =
//                new WebFilterChainServerAuthenticationSuccessHandler();
////        successHandler.onAuthenticationSuccess((exchange, url) -> {
////            exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
////            exchange.getResponse().getHeaders().setLocation(url);
////            return exchange;
////        });
////        successHandler.setDefaultTargetURL("/"); // Set the default URL to redirect to upon successful authentication
//        successHandler.onAuthenticationSuccess("/");
//        return successHandler;
//    }
}

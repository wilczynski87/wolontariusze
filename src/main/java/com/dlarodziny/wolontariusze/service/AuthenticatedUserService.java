package com.dlarodziny.wolontariusze.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dlarodziny.wolontariusze.model.AuthenticatedUser;
import com.dlarodziny.wolontariusze.repository.VolunteerRepo;

import reactor.core.publisher.Mono;

@Component
public class AuthenticatedUserService implements ReactiveUserDetailsService {

    private final VolunteerRepo volunteerRepo;

    AuthenticatedUserService(VolunteerRepo volunteerRepo) {
        this.volunteerRepo = volunteerRepo;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return volunteerRepo.findByUsername(username).map(AuthenticatedUser::new);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }
    
}
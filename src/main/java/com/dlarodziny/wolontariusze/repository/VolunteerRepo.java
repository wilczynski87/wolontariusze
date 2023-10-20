package com.dlarodziny.wolontariusze.repository;

import com.dlarodziny.wolontariusze.model.Volunteer;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface VolunteerRepo extends R2dbcRepository<Volunteer, Long> {
    Mono<Volunteer> findByUsername(String username);
}

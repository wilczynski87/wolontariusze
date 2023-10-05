package com.dlarodziny.wolontariusze.repository;

import com.dlarodziny.wolontariusze.model.VolunteerDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface VolunteerDetailsRepo extends R2dbcRepository<VolunteerDetails, Long> {

    Mono<VolunteerDetails> getVolunteerDetailsByPatron(Long patron);

    @Query("SELECT * from volunteerdetails vd join volunteer v on vd.patron = v.id where v.username = :s")
    Mono<VolunteerDetails> getVolunteerDetailsByUsername(String username);
}

package com.dlarodziny.wolontariusze.service;

import com.dlarodziny.wolontariusze.model.VolunteerDetails;
import com.dlarodziny.wolontariusze.repository.VolunteerDetailsRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VolunteerDetailsService {
    private final VolunteerDetailsRepo volunteerDetailsRepo;

    public VolunteerDetailsService(VolunteerDetailsRepo volunteerDetailsRepo) {
        this.volunteerDetailsRepo = volunteerDetailsRepo;
    }

    public Mono<VolunteerDetails> getVolunteerDetailsByPatron(Long patron) {
        return volunteerDetailsRepo.getVolunteerDetailsByPatron(patron);
    }
    public Mono<VolunteerDetails> getVolunteerDetailsByUsername(String username) {
        return volunteerDetailsRepo.getVolunteerDetailsByUsername(username)
                ;
    }
    public Mono<VolunteerDetails> addVolunteerDetails(Long patron, VolunteerDetails volunteerDetails) {
        volunteerDetails.setPatron(patron);
        return volunteerDetailsRepo.save(volunteerDetails);
    }
    public Mono<VolunteerDetails> updateVolunteerDetails(VolunteerDetails volunteerDetails, Long id) {
        return volunteerDetailsRepo.getVolunteerDetailsByPatron(id)
                .defaultIfEmpty(new VolunteerDetails(id))
                .map(old -> {
                    volunteerDetails.setId(old.getId());
                    volunteerDetails.setPatron(old.getPatron());
                    volunteerDetails.setStarted(old.getStarted());
                    // volunteerDetails.setLastActivity(old.getLastActivity());
                    // volunteerDetails.setEnded(old.getEnded());
                    return volunteerDetails;
                })
                .flatMap(volunteerDetailsRepo::save);
    }
}

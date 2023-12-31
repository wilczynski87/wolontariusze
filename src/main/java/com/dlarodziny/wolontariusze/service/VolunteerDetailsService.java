package com.dlarodziny.wolontariusze.service;

import com.dlarodziny.wolontariusze.model.VolunteerDetails;
import com.dlarodziny.wolontariusze.repository.VolunteerDetailsRepo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VolunteerDetailsService {
    private final VolunteerDetailsRepo volunteerDetailsRepo;
    private final VolunteerService volunteerService;

    public VolunteerDetailsService(VolunteerDetailsRepo volunteerDetailsRepo, VolunteerService volunteerService) {
        this.volunteerDetailsRepo = volunteerDetailsRepo;
        this.volunteerService = volunteerService;
    }

    public Mono<VolunteerDetails> getVolunteerDetailsByPatron(Long patron) {
        return volunteerDetailsRepo.getVolunteerDetailsByPatron(patron);
    }
    public Mono<VolunteerDetails> getVolunteerDetailsByUsername(String username) {
        return volunteerDetailsRepo.getVolunteerDetailsByUsername(username)
                ;
    }
    public Flux<VolunteerDetails> getAllVolunteerDetails() {
        return volunteerDetailsRepo.findAll();
    }
    public Mono<VolunteerDetails> addVolunteerDetails(Long patron, VolunteerDetails volunteerDetails) {
        volunteerDetails.setPatron(patron);
        return volunteerDetailsRepo.save(volunteerDetails);
    }

    public Flux<VolunteerDetails> saveVolunteerDetails(List<VolunteerDetails> volunteerDetails) {
        return volunteerDetailsRepo.saveAll(volunteerDetails);
    }

    public Mono<VolunteerDetails> updateVolunteerDetails(VolunteerDetails volunteerDetails, Long id) {
        return volunteerDetailsRepo.getVolunteerDetailsByPatron(id)
                .defaultIfEmpty(new VolunteerDetails(id))
                .map(old -> {
                    volunteerDetails.setId(old.getId());
                    volunteerDetails.setPatron(old.getPatron());
                    volunteerDetails.setStarted(old.getStarted());
                    if(volunteerDetails.getEnded().isBefore(LocalDate.now())) volunteerService.updateActiveStatus(volunteerDetails.getPatron());
                    return volunteerDetails;
                })
                .flatMap(volunteerDetailsRepo::save);
    }
}

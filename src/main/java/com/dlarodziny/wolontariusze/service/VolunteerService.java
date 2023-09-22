package com.dlarodziny.wolontariusze.service;

import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.repository.VolunteerRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VolunteerService {

    private final VolunteerRepo volunteerRepo;


    public VolunteerService(VolunteerRepo volunteerRepo) {
        this.volunteerRepo = volunteerRepo;
    }

    public Flux<Volunteer> getAllVlounteers() {
        return volunteerRepo.findAll();
    }

    public Mono<Volunteer> addVolunteer(Volunteer volunteer) {
        return volunteerRepo.save(volunteer);
    }
}

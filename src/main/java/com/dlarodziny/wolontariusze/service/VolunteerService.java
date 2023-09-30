package com.dlarodziny.wolontariusze.service;

import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.repository.VolunteerRepo;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VolunteerService {
    private final VolunteerRepo volunteerRepo;
    public VolunteerService(VolunteerRepo volunteerRepo) {
        this.volunteerRepo = volunteerRepo;
    }

    public Mono<UserDetails> findByUsername(String username) {
        return volunteerRepo.findByUsername(username).map(Volunteer::toAuthUser);
    }
    public Flux<Volunteer> getAllVlounteers() {
        return volunteerRepo.findAll();
    }

    public Mono<Volunteer> addVolunteer(Volunteer volunteer) {
        return volunteerRepo.save(volunteer);
    }

    public Mono<Volunteer> getVolunteerById(Long id) {
        return volunteerRepo.findById(id);
    }
    public Mono<Volunteer> getVolunteerByUsername(String username){ return volunteerRepo.findByUsername(username);}

    public Mono<Void> deleteVolunteer(Long id) {
        return volunteerRepo.deleteById(id);
    }

    public Mono<Volunteer> updateVolunteer(Volunteer volunteer) {
        return volunteerRepo.findById(volunteer.getId())
                .flatMap(found -> volunteerRepo.save(volunteer))
                        .then(volunteerRepo.findById(volunteer.getId()));
    }
}

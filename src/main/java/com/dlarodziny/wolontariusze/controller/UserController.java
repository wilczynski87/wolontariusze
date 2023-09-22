package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final VolunteerService volunteerService;

    public UserController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("getAllUsers")
    public Flux<Volunteer> getAllUsers() {
        return volunteerService.getAllVlounteers();
    }

    @PostMapping("addUser")
    public Mono<Volunteer> addUser(Volunteer volunteer) {
        return volunteerService.addVolunteer(volunteer);
    }
}

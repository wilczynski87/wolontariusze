package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.repository.VolunteerRepo;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Volunteer> addUser(@RequestBody Volunteer volunteer) {
        return volunteerService.addVolunteer(volunteer);
    }

    @GetMapping("/getUserById")
    public Mono<Volunteer> getUserById(@RequestParam Long id) {
        return volunteerService.getVolunteerById(id);
    }

    @DeleteMapping("/deleteUser")
    public Mono<Void> deleteUser(@RequestParam Long id) {
        return volunteerService.deleteVolunteer(id);
    }

    @PatchMapping("/updateUser")
    public Mono<Volunteer> updateUser(@RequestBody Volunteer volunteer) {
        return volunteerService.updateVolunteer(volunteer);
    }

}

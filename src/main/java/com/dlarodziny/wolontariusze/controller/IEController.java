package com.dlarodziny.wolontariusze.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.result.view.Rendering;

import com.dlarodziny.wolontariusze.model.Contact;
import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.model.VolunteerDetails;
import com.dlarodziny.wolontariusze.service.ContactService;
import com.dlarodziny.wolontariusze.service.IEService;
import com.dlarodziny.wolontariusze.service.VolunteerDetailsService;
import com.dlarodziny.wolontariusze.service.VolunteerService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class IEController {

    private final IEService ieService;
    private final VolunteerService volunteerService;
    private final VolunteerDetailsService volunteerDetailsService;
    private final ContactService contactService;

    public IEController(IEService ieService, VolunteerService volunteerService, VolunteerDetailsService volunteerDetailsService, ContactService contactService) {
        this.ieService = ieService;
        this.volunteerService = volunteerService;
        this.volunteerDetailsService = volunteerDetailsService;
        this.contactService = contactService;
    }

    @GetMapping("/getVolunteers")
    public Mono<Rendering> importVolunteers() {
        var id = "1naw-HPYreUYJ7egwRQm6p4H26F_db241DX6m71BJbCA";
        var fold = "Opiekun";
        var scope = "A5:G100";
        return Mono.just(Rendering.view("fragments/loader :: importVolunteersLoader")
            .modelAttribute("msg", ieService.getVolunteers(id, fold, scope))
            .modelAttribute("sev", "info")
            .build());
    }

    @GetMapping("/getContacts")
    public Mono<Rendering> importContacts() {
        var id = "1naw-HPYreUYJ7egwRQm6p4H26F_db241DX6m71BJbCA";
        var fold = "CRM";
        var scope = "A5:G100";
        return Mono.just(Rendering.view("fragments/loader :: importVolunteersLoader")
            .modelAttribute("msg", ieService.getContacts(id, fold, scope))
            .modelAttribute("sev", "info")
            .build());
    }

    @ResponseBody
    @PostMapping("/saveVolunteers")
    public Flux<Volunteer> saveVolunteers(@RequestBody List<Volunteer> volunteers, final Model model, Authentication auth) {
        log.info("\n\nsaveVolunteers dotknięte!!!\n");
        log.info("Volunteer: {}", volunteers);

        return volunteerService.addVolunteers(volunteers);
    }

    @ResponseBody
    @PostMapping("/saveVolunteerDetails")
    public Flux<VolunteerDetails> saveVolunteerDetails(@RequestBody List<VolunteerDetails> volunteerDetails, final Model model, Authentication auth) {
        log.info("\n\nsaveVolunteerDetails dotknięte!!!\n");
        log.info("VolunteerDetails: {}", volunteerDetails);

        return volunteerDetailsService.saveVolunteerDetails(volunteerDetails);
    }

    // Export data
    @ResponseBody
    @PostMapping("/exportVolunteers")
    public Flux<Volunteer> exportVolunteers(final Model model, Authentication auth) {
        log.info("\n\nexportVolunteers dotknięte!!!\n");

        return volunteerService.getAllVlounteers();
    }

    @ResponseBody
    @PostMapping("/exportVolunteerDetails")
    public Flux<VolunteerDetails> exportVolunteerDetails(final Model model, Authentication auth) {
        log.info("\n\nexportVolunteerDetails dotknięte!!!\n");

        return volunteerDetailsService.getAllVolunteerDetails();
    }

    @ResponseBody
    @PostMapping("/exportContacts")
    public Flux<Contact> exportContacts(final Model model, Authentication auth) {
        log.info("\n\nexportContacts dotknięte!!!\n");

        return contactService.getAllContacts();
    }

}
package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Attitude;
import com.dlarodziny.wolontariusze.model.UserWithDetails;
import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.model.VolunteerDetails;
import com.dlarodziny.wolontariusze.service.VolunteerDetailsService;
import com.dlarodziny.wolontariusze.service.VolunteerService;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class UserViewController {

    private final VolunteerDetailsService volunteerDetailsService;
    private final VolunteerService volunteerService;

    public UserViewController(VolunteerDetailsService volunteerDetailsService, VolunteerService volunteerService) {
        this.volunteerDetailsService = volunteerDetailsService;
        this.volunteerService = volunteerService;
    }

    @GetMapping("/userDataForm")
    public Mono<Rendering> userDataForm(final Model model, final WebSession session, Authentication authentication) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm :: userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerByUsername(authentication.getName()))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()))
                        .modelAttribute("adminRole", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .modelAttribute("nameOfUser", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()).map(VolunteerDetails::getName))
                        .modelAttribute("editVolunteer", "poprawmy twoj dane")
                        .build());
    }

    @PostMapping("/saveUserData")
    public Mono<Rendering> saveUserDataForm(final Model model, final WebSession session, Authentication authentication) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm :: userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerByUsername(authentication.getName()))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()))
                        .modelAttribute("adminRole", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .modelAttribute("nameOfUser", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()).map(VolunteerDetails::getName))
                        .build());
    }

    @GetMapping("/volunteerDataForm/{volunteerId}")
    public Mono<Rendering> volunteerDataForm(final Model model, final WebSession session, Authentication authentication, @PathVariable Long volunteerId) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm :: userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerById(volunteerId))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByPatron(volunteerId))
                        .modelAttribute("adminRole", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .modelAttribute("nameOfUser", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()).map(VolunteerDetails::getName))
                        .modelAttribute("editVolunteer", "zmieńmy dane urzytkownika ")
                        .build());
    }
    
    @GetMapping("/addVolunteer")
    public Mono<Rendering> addVolunteer(final Model model, final WebSession session, Authentication authentication) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm :: addUserDataForm")
                    .modelAttribute("volunteer", new Volunteer())
                    .modelAttribute("volunteerDetails", new VolunteerDetails())
                    .modelAttribute("adminRole", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .modelAttribute("editVolunteer", "dodajmy nowego urzytkownika")
                    .modelAttribute("nameOfUser", "Panie administratorze ")
                    .modelAttribute("roles", com.dlarodziny.wolontariusze.model.Role.values())
                    .build());
    }

    @GetMapping("/checkOnVolunteers")
    // public Mono<Rendering> checkOnVolunteers(final Model model, final WebSession session, Authentication authentication, UserWithDetails userWithDetails) {
    public Mono<Rendering> checkOnVolunteers(final Model model, final WebSession session, Authentication authentication) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("fragments/volunteers :: volunteers")
                    .modelAttribute("volunteers", volunteerService.getAllVlounteers())
                    .modelAttribute("volunteersDetails", volunteerDetailsService.getAllVolunteerDetails())
                    .modelAttribute("attitudes", Arrays.stream(Attitude.values()).map(Attitude::getAttitude).toArray())
                    .modelAttribute("adminRole", authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .modelAttribute("nameOfUser", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()).map(VolunteerDetails::getName))
                    .build());
    }

    @PostMapping("/saveUser")
    public Mono<Rendering> saveUserWithDetails(final Model model, final WebSession session, Authentication authentication, UserWithDetails userWithDetails) {
        return setRedirectAttributes(model, session)
            .thenReturn(Rendering.view("fragments/loader :: loader")
                .modelAttribute("userSaved", volunteerService.addVolunteer(userWithDetails.getVolunteer())
                    .map(Volunteer::getId)
                    .subscribe(volunteerId -> volunteerDetailsService.addVolunteerDetails(volunteerId, userWithDetails.getVolunteerDetails()).subscribe()))
                .modelAttribute("msg", "Zapisuje użytkownika")
                .modelAttribute("sev", "info")
                .build());
    }

    @GetMapping("/checkNewUser")
    public Mono<Rendering> saveUserWithDetails(final Model model, final WebSession session, Authentication authentication, String username) {
        return setRedirectAttributes(model, session)
            .thenReturn(Rendering.view("fragments/loader :: nickExists")
                .modelAttribute("nickExists", volunteerService.isVolunteerSaved(username))
                .build());
    }

    @GetMapping("/saveSuccess/{username}")
    public Mono<Rendering> userSaved(final Model model, final WebSession session, @PathVariable String username) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("fragments/loader :: saveSuccess")
                    .header("HX-Trigger", "userAdded")
                    .modelAttribute("userSaved", volunteerService.isVolunteerSaved(username))
                    .build());
    }

    private Mono<Void> setRedirectAttributes(final Model model, final WebSession session) {
        return Mono.fromRunnable(
                () -> {
                    if(session.getAttribute("MSG_SUCCESS") != null) {
                        model.addAttribute("MSG_SUCCESS", session.getAttribute("MSG_SUCCESS"));
                        session.getAttributes().remove("MSG_SUCCESS");
                    }

                });
    }
}

package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.model.VolunteerDetails;
import com.dlarodziny.wolontariusze.service.VolunteerDetailsService;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

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
//        System.out.println(authentication);
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerByUsername(authentication.getName()))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()))
                        .build());
    }

    @PostMapping("/saveUserData")
    public Mono<Rendering> saveUserDataForm(final Model model, final WebSession session, Authentication authentication) {
//        System.out.println(authentication);
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerByUsername(authentication.getName()))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()))
                        .build());
    }

    @GetMapping("/volunteerDataForm/{volunteerId}")
    public Mono<Rendering> volunteerDataForm(final Model model, final WebSession session, Authentication authentication, @PathVariable Long volunteerId) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerById(volunteerId))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByPatron(volunteerId))
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

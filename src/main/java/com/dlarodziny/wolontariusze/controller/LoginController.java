package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.service.ContactService;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Controller
public class LoginController {

    private final VolunteerService volunteerService;
    private final ContactService contactService;

    public LoginController(VolunteerService volunteerService, ContactService contactService) {
        this.volunteerService = volunteerService;
        this.contactService = contactService;
    }

//    @GetMapping("/")
//    public String index() {
//        return "login";
//    }

    @GetMapping("/")
    public Mono<Rendering> list(final Model model, final WebSession session, Authentication authentication) {
        System.out.println(authentication);
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("login")
                        .modelAttribute("volunteer", authentication.getName())
                        .build());
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board")
    public Mono<Rendering> volunteerDashboard(final Model model, final WebSession session, Authentication authentication) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("volunteerDashboard")
                        .modelAttribute("volunteer", authentication.getName())
                        .modelAttribute("contacts", contactService.getAllContactsByUserName(authentication.getName()))
                        .build());
    }

//    @GetMapping("/getAllUsers")
//    public Flux<Volunteer> getAllUsers(final Model model, final WebSession session) {
//        return volunteerService.getAllVlounteers();
//    }

    private Mono<Void> setRedirectAttributes(final Model model, final WebSession session) {
        return Mono.fromRunnable(
                () -> {
                    if(session.getAttribute("MSG_SUCCESS") != null) {
                        model.addAttribute("MSG_SUCCESS", session.getAttribute("MSG_SUCCESS"));
                        session.getAttributes().remove("MSG_SUCCESS");
                    }

                });
    }
    private Mono<Void> addRedirectAttributes(final WebSession session, final String key, final String value) {
        return Mono.fromRunnable(
                () -> {
                    if(session.getAttribute("MSG_SUCCESS") == null) {
                        session.getAttributes().put(key, value);
                    }
                });
    }
}

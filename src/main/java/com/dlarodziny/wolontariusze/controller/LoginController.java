package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class LoginController {

    private final VolunteerService volunteerService;

    public LoginController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

//    @GetMapping("/")
//    public String index() {
//        return "login";
//    }

    @GetMapping("/")
    public Mono<Rendering> list(final Model model, final WebSession session) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("login")
                        .modelAttribute("greetings", "Greetings dear Volunteer!")
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

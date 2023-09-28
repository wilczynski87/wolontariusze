package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Attitude;
import com.dlarodziny.wolontariusze.model.Contact;
import com.dlarodziny.wolontariusze.service.ContactService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Controller
public class ContactViewController {
    private final ContactService contactService;
    public ContactViewController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/commentForm")
    public Mono<Rendering> contactForm(final Model model, final WebSession session) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("fragments/contactForm :: contactForm")
                        .modelAttribute("attitudes", Arrays.stream(Attitude.values()).map(Attitude::getAttitude).toArray())
                        .build());
    }

    @PostMapping("/contactTable")
    public Mono<Rendering> addToContactTable(final Model model, final WebSession session, final Authentication authentication, Contact contact) {
//        volunteerService.getPatronIdFromName
        var patronId = authentication.getName();
        System.out.println(contact);
//        System.out.println(contactService.addContact(contact));
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("fragments/contactTable")
                        .modelAttribute("contacts", contactService.getAllContactsByUserName(authentication.getName()))
                        .build());
    }
    @GetMapping("/contactTable")
    public Mono<Rendering> getContactTable(final Model model, final WebSession session, final Authentication authentication) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("fragments/contactTable")
                        .modelAttribute("contacts", contactService.getAllContactsByUserName(authentication.getName()))
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

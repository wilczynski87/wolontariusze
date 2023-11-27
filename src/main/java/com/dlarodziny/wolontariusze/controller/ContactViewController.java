package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Contact;
import com.dlarodziny.wolontariusze.model.DIRECTION;
import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.service.ContactService;
import com.dlarodziny.wolontariusze.service.VolunteerDetailsService;
import com.dlarodziny.wolontariusze.service.VolunteerService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class ContactViewController {

    private final ContactService contactService;
    private final VolunteerService volunteerService;
    private final VolunteerDetailsService volunteerDetailsService;

    public ContactViewController(ContactService contactService, VolunteerService volunteerService, VolunteerDetailsService volunteerDetailsService) {
        this.contactService = contactService;
        this.volunteerService = volunteerService;
        this.volunteerDetailsService = volunteerDetailsService;
    }

    @GetMapping("/commentForm")
    public Mono<Rendering> contactForm(final Model model, final WebSession session) {
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("fragments/contactForm :: contactForm")
                    .build());
    }

    @PostMapping("/contactTable")
    public Mono<Rendering> addToContactTable(final Model model, final WebSession session, final Authentication authentication, Contact contact, 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size, 
        @RequestParam(defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        var adminRole = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        var savedContact = volunteerService.getVolunteerByUsername(authentication.getName())
                .map(Volunteer::getId)
                .flatMap(id -> {
                    contact.setPatron(id);
                    return contactService.addContact(contact);
                });

        return setRedirectAttributes(model, session)
                .then(savedContact)
                .thenReturn(Rendering.view("fragments/contactTable")
                        .modelAttribute("contacts", adminRole 
                            ? contactService.getAllContactsBy(pageable)
                            : contactService.getAllContactsByUserName(authentication.getName(), pageable))
                        .modelAttribute("adminRole", adminRole)
                        .modelAttribute("patrons", volunteerDetailsService.getAllVolunteerDetails())
                        .build());
    }

    @GetMapping("/contactTable")
    public Mono<Rendering> getContactTable(final Model model, final Authentication authentication, 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size, 
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "DESC") DIRECTION direction
        ) {
            System.out.println(direction);
        var adminRole = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.valueOf(direction.name()), sort));
        
        return Mono.just(Rendering.view("fragments/contactTable")
            .modelAttribute("contacts", adminRole 
                ? contactService.getAllContactsBy(pageable)
                : contactService.getAllContactsByUserName(authentication.getName(), pageable))
            .modelAttribute("adminRole", adminRole)
            .modelAttribute("patrons", volunteerDetailsService.getAllVolunteerDetails())
            .modelAttribute("pagination", pageable)
            .modelAttribute("direction", direction.equals(DIRECTION.ASC) ? DIRECTION.DESC : DIRECTION.ASC)
            .build());
    }
    @GetMapping("/contactTableByContactName")
    public Mono<Rendering> getContactTableByContactName(final Model model, final Authentication authentication, 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size, 
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "DESC") DIRECTION direction,
        @RequestParam(defaultValue = "") String contactName
        ) {
        var adminRole = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.valueOf(direction.name()), sort));
        
        return Mono.just(Rendering.view("fragments/contactTable")
            .modelAttribute("contacts", adminRole 
                ? contactService.getAllContactsByAndContactName(pageable, contactName)
                : contactService.getAllContactsByUserName(authentication.getName(), pageable))
            .modelAttribute("adminRole", adminRole)
            .modelAttribute("patrons", volunteerDetailsService.getAllVolunteerDetails())
            .modelAttribute("pagination", pageable)
            .modelAttribute("direction", direction.equals(DIRECTION.ASC) ? DIRECTION.DESC : DIRECTION.ASC)
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

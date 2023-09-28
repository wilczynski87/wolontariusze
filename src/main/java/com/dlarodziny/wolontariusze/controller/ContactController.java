package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.model.Contact;
import com.dlarodziny.wolontariusze.service.ContactService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ContactController {
    private final ContactViewController contactViewController;
    private final ContactService contactService;

    public ContactController(LoginController loginController, ContactViewController contactViewController, ContactService contactService) {
        this.contactViewController = contactViewController;
        this.contactService = contactService;
    }

    @GetMapping("/getAllContacts")
    public Flux<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @PostMapping("/addContact")
    public Mono<Contact> addContact(@RequestBody Contact contact) {
        System.out.println(contact);
        return contactService.addContact(contact);
    }

    @GetMapping("/AllContactsForUser")
    public Flux<Contact> getAllContactsByUserId(@RequestParam Long id) {
        return contactService.getAllContactsByUserId(id);
    }
//    @PostMapping("/contactTable")
//    public Mono<Void> addToContactTable(Contact contact) {
//        System.out.println(contact);
//        return null;
//    }

//    @PatchMapping("/updateContact")
}

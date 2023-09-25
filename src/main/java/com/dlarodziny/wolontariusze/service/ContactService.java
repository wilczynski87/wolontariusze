package com.dlarodziny.wolontariusze.service;

import com.dlarodziny.wolontariusze.model.Contact;
import com.dlarodziny.wolontariusze.repository.ContactRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching;

@Service
public class ContactService {
    private final ContactRepo contactRepo;

    public ContactService(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public Flux<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    public Mono<Contact> addContact(Contact contact) {
        return contactRepo.save(contact);
    }

    public Flux<Contact> getAllContactsByUserId(Long id) {
        return contactRepo.findAllByPatron(id);
    }
}

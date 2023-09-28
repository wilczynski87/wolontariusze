package com.dlarodziny.wolontariusze.service;

import com.dlarodziny.wolontariusze.model.Attitude;
import com.dlarodziny.wolontariusze.model.Contact;
import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.repository.ContactRepo;
import com.dlarodziny.wolontariusze.repository.VolunteerRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ContactService {
    private final ContactRepo contactRepo;
    private final VolunteerRepo volunteerRepo;

    public ContactService(ContactRepo contactRepo, VolunteerRepo volunteerRepo) {
        this.contactRepo = contactRepo;
        this.volunteerRepo = volunteerRepo;
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
    public Flux<Contact> getAllContactsByUserName(String username) {
        return contactRepo.findContactByPatronUsername(username);
    }
}

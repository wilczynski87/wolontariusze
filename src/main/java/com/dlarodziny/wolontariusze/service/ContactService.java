package com.dlarodziny.wolontariusze.service;

import com.dlarodziny.wolontariusze.model.Contact;
import com.dlarodziny.wolontariusze.model.DIRECTION;
import com.dlarodziny.wolontariusze.repository.ContactRepo;
import com.dlarodziny.wolontariusze.repository.VolunteerRepo;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ContactService {
    private final ContactRepo contactRepo;
    private final VolunteerDetailsService volunteerDetailsService;

    public ContactService(ContactRepo contactRepo, VolunteerDetailsService volunteerDetailsService) {
        this.contactRepo = contactRepo;
        this.volunteerDetailsService = volunteerDetailsService;
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
    public Mono<Page<Contact>> getAllContactsByUserName(final String username, final Pageable pageable) {
        final int pageSize = pageable.getPageSize();
        final int currentPage = pageable.getPageNumber(); 

        return contactRepo.findContactsByPatronUsernameAndPagable(username, pageable).collectList().zipWith(this.contactRepo.findContactsByPatronUsername(username).count())
                              .map(i -> new PageImpl<>(i.getT1(), PageRequest.of(currentPage, pageSize), i.getT2()));
    }

    public Mono<Page<Contact>> getAllContactsBy(final Pageable pageable) {

        return contactRepo.findAllBy(pageable).collectList().zipWith(this.contactRepo.count())
                              .map(i -> new PageImpl<>(i.getT1(), pageable, i.getT2()));
    }

    public Mono<Page<Contact>> getAllContactsByContactNameAndPatron(final Pageable pageable, final String contactName, Long patron) {

        return patron == null 
        ? contactRepo.findByContactNameContaining(contactName, pageable)
            .collectList()
            .zipWith(contactRepo.countByContactNameContaining(contactName))
            .map(i -> new PageImpl<>(i.getT1(), pageable, i.getT2()))
        : contactRepo.findByContactNameContainingAndPatron(contactName, patron, pageable)
            .collectList()
            .zipWith(contactRepo.countByContactNameContainingAndPatron(contactName, patron))
            .map(i -> new PageImpl<>(i.getT1(), pageable, i.getT2()));
    }

    public Mono<Page<Contact>> getAllContactsByContactNameAndPatron(final Pageable pageable, final String contactName, Mono<Long> patron) {

        return patron == null 
        ? contactRepo.findByContactNameContaining(contactName, pageable)
            .collectList()
            .zipWith(contactRepo.countByContactNameContaining(contactName))
            .map(i -> new PageImpl<>(i.getT1(), pageable, i.getT2()))
        : contactRepo.findByContactNameContainingAndPatron(contactName, patron, pageable)
            .collectList()
            .zipWith(contactRepo.countByContactNameContainingAndPatron(contactName, patron))
            .map(i -> new PageImpl<>(i.getT1(), pageable, i.getT2()));
    }
}

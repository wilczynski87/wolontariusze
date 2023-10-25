package com.dlarodziny.wolontariusze.repository;

import com.dlarodziny.wolontariusze.model.Contact;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ContactRepo extends R2dbcRepository<Contact, Long> {
    Flux<Contact> findAllBy(Example<Contact> example);

    Flux<Contact> findAllByPatron(Long id);

    @Query("SELECT * from contact c join volunteer v on v.id = c.patron where v.username = :1")
    Flux<Contact> findContactsByPatronUsername(String username);

    @Query("SELECT * from contact c join volunteer v on v.id = c.patron where v.username = :1")
    Flux<Contact> findContactsByPatronUsernameAndPagable(String username, Pageable pageable);

    Flux<Contact> findAllBy(Pageable pageable);

    // Flux<Contact> findAllbyPatronAndPagable(String username, Pageable pageable);


}

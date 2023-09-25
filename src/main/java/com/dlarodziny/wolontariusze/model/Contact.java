package com.dlarodziny.wolontariusze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("CONTACT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private Long id;
    private String contactName;
    private String phone;
    private String email;
    private LocalDate lastContact;
    private int attitude;
    private String comment;
    private Long patron;

    public Contact(Long patron) {
        this();
        this.patron = patron;
    }
}

package com.dlarodziny.wolontariusze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.Period;

@Table("VOLUNTEERDETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerDetails {
    @Id
    private Long id;
    private Long patron;
    private String name;
    private String surname;
    private LocalDate dob;
    private LocalDate started;
    private LocalDate ended;
    private LocalDate lastActivity;

    public String lasting() {
        return Period.between(this.started, LocalDate.now()).toString();
    }
}

package com.dlarodziny.wolontariusze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

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
    private String phone;
    private String email;
    private String address;

    public String lasting() {
        return Period.between(this.started, LocalDate.now()).toString();
    }

    public VolunteerDetails(Long patron) {
        this.patron = patron;
    }

    public static VolunteerDetails getVolunteerDetails(VolunteerDetails[] volunteerDetails, Long id) {
        System.out.println(volunteerDetails);
        return Arrays.stream(volunteerDetails)
            .filter(details -> details.getPatron().equals(id))
            .findFirst()
            .orElse(null);
    }

    
}

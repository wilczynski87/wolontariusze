package com.dlarodziny.wolontariusze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;

@Table("VOLUNTEER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Volunteer {
    @Id
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean active;

    public AuthenticatedUser toAuthUser() {
         return new AuthenticatedUser(this.username, this.password);
    }

    public VolunteerDetails getVolunteerDetails(VolunteerDetails[] volunteerDetails) {
        return Arrays.stream(volunteerDetails)
            .filter(details -> details.getPatron().equals(this.id))
            .findFirst()
            .orElse(null);
    }

    public String readRole() {
        return this.role.equals("ROLE_ADMIN") ? Role.ADMIN.getRoleDesc() : Role.USER.getRoleDesc();
    }

}

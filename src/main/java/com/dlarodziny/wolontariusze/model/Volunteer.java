package com.dlarodziny.wolontariusze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collections;

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

}

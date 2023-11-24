package com.dlarodziny.wolontariusze.model;

import lombok.Data;

@Data
public class UserWithDetails extends VolunteerDetails {
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean active = true;

    public Volunteer getVolunteer() {
        return new Volunteer(this.id, this.username, this.password, this.role == null ? "USER" : Role.valueOf(this.role).getSecurityRole(), this.active);
    }

    public void breakVolunteer(Volunteer volunteer) {
        this.id = volunteer.getId();
        this.username = volunteer.getUsername();
        this.password = volunteer.getPassword();
        this.role = volunteer.getRole() == null ? "USER" : this.role;
        this.active = volunteer.isActive();
    }

    public VolunteerDetails getVolunteerDetails() {
        return new VolunteerDetails(null, this.id, this.name, this.surname, this.dob, this.started, this.ended, this.lastActivity, this.phone, this.email, this.address);
    }

    
}
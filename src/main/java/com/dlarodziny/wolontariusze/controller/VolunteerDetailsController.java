package com.dlarodziny.wolontariusze.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlarodziny.wolontariusze.model.Volunteer;
import com.dlarodziny.wolontariusze.model.VolunteerDetails;
import com.dlarodziny.wolontariusze.service.VolunteerDetailsService;
import com.dlarodziny.wolontariusze.service.VolunteerService;

@RestController
public class VolunteerDetailsController {

    public final VolunteerDetailsService volunteerDetailsService;
    public final VolunteerService volunteerService;

    public VolunteerDetailsController(VolunteerDetailsService volunteerDetailsService, VolunteerService volunteerService) {
        this.volunteerDetailsService = volunteerDetailsService;
        this.volunteerService = volunteerService;
    }

    @PatchMapping("/updateUserDetails")
    public void updateUser(VolunteerDetails volunteerDetails, final Authentication authentication) {
        volunteerService.getVolunteerByUsername(authentication.getName())
            .map(Volunteer::getId)
            .subscribe(userId -> volunteerDetailsService.updateVolunteerDetails(volunteerDetails, userId).subscribe());
    }
           
}
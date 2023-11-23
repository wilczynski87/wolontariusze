package com.dlarodziny.wolontariusze.crown;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dlarodziny.wolontariusze.repository.VolunteerDetailsRepo;
import com.dlarodziny.wolontariusze.repository.VolunteerRepo;

@Component
public class VolunteerStatus {
    
    private final VolunteerRepo volunteerRepo;
    private final VolunteerDetailsRepo volunteerDetailsRepo;

    public VolunteerStatus(VolunteerRepo volunteerRepo, VolunteerDetailsRepo volunteerDetailsRepo) {
        this.volunteerRepo = volunteerRepo;
        this.volunteerDetailsRepo = volunteerDetailsRepo;
    }

    @Scheduled(cron = "@midnight")
    private void checkVolunteerStatus() {
        volunteerDetailsRepo.findAllByEnded(LocalDate.now())
            .doOnNext(vd -> disableVolunteerIfActiveIsPastDue(vd.getPatron()))
            .subscribe()
            ;
    }

    private void disableVolunteerIfActiveIsPastDue(Long id) {
        volunteerRepo.findById(id)
            .doOnNext(v -> {
                if(v.isActive()) {
                    v.setActive(false);
                    volunteerRepo.save(v).subscribe();
                }
            })
            .subscribe();
    }


}
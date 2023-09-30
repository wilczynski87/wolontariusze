package com.dlarodziny.wolontariusze.controller;

import com.dlarodziny.wolontariusze.service.VolunteerDetailsService;
import com.dlarodziny.wolontariusze.service.VolunteerService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Controller
public class UserViewController {

    private final VolunteerDetailsService volunteerDetailsService;
    private final VolunteerService volunteerService;

    public UserViewController(VolunteerDetailsService volunteerDetailsService, VolunteerService volunteerService) {
        this.volunteerDetailsService = volunteerDetailsService;
        this.volunteerService = volunteerService;
    }

    @GetMapping("/userDataForm")
    public Mono<Rendering> userDataForm(final Model model, final WebSession session, Authentication authentication) {
//        System.out.println(authentication);
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerByUsername(authentication.getName()))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()))
                        .build());
    }

    @PostMapping("/saveUserData")
    public Mono<Rendering> saveUserDataForm(final Model model, final WebSession session, Authentication authentication) {
//        System.out.println(authentication);
        return setRedirectAttributes(model, session)
                .thenReturn(Rendering.view("userDataForm")
                        .modelAttribute("volunteer", volunteerService.getVolunteerByUsername(authentication.getName()))
                        .modelAttribute("volunteerDetails", volunteerDetailsService.getVolunteerDetailsByUsername(authentication.getName()))
                        .build());
    }


    //    @GetMapping("/userDataForm")
//    public Mono<String> userDataForm() {
//        return Mono.just("redirect:/userDataForm");
//    }
//    @GetMapping("userDataForm")
//    public Mono<ResponseEntity<String>> hiRedirectRespEntity() {
//        return Mono.just(new HttpHeaders())
//                .doOnNext(header -> header.add("Location", "/userDataForm"))
//                .map(header -> new ResponseEntity<>(null, header, HttpStatus.MOVED_PERMANENTLY));
//    }
//    @GetMapping("/userDataForm")
//    public String index(final Model model, final Authentication authentication) {
//        System.out.println(authentication);
//        // data streaming, data driven mode.
////        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
////                new ReactiveDataDriverContextVariable(movieRepository.findAll(), 1);
//
////        model.addAttribute("movies", reactiveDataDrivenMode);
//
//        return "userDataForm";
//    }

    private Mono<Void> setRedirectAttributes(final Model model, final WebSession session) {
        return Mono.fromRunnable(
                () -> {
                    if(session.getAttribute("MSG_SUCCESS") != null) {
                        model.addAttribute("MSG_SUCCESS", session.getAttribute("MSG_SUCCESS"));
                        session.getAttributes().remove("MSG_SUCCESS");
                    }

                });
    }
}

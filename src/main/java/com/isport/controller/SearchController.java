package com.isport.controller;

import com.isport.model.Event;
import com.isport.model.User;
import com.isport.service.EventService;
import com.isport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.Year;
import java.util.Date;
import java.util.List;

import static com.isport.constant.ISportConstants.CURRENT_USER;
import static com.isport.constant.ISportConstants.EVENTS;
import static com.isport.constant.ISportConstants.YEAR;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final UserService userService;
    private final EventService eventService;

    @GetMapping("/search")
    public String search(Principal principal, Model model) {
        User currentUser = userService.findUser(principal.getName());
        model.addAttribute(CURRENT_USER, currentUser);
        model.addAttribute(YEAR, Year.now());
        model.addAttribute(EVENTS, eventService.getNonUserEvents(currentUser, new Date()));
        return "search";
    }

    @PostMapping("/search")
    public String search(Principal principal, @RequestParam("search") String search,
                         @RequestParam("selectedOption") String selectedOption, Model model) {
        User currentUser = userService.findUser(principal.getName());
        model.addAttribute(CURRENT_USER, currentUser);
        model.addAttribute(YEAR, Year.now());

        Date todayDate = new Date();
        List<Event> events = switch (selectedOption) {
            case "eventName" -> eventService.findByEventName(search, todayDate);
            case "locationName" -> eventService.findByLocation(search, todayDate);
            case "creator" -> eventService.findByCreatorName(search, todayDate);
            default -> null;
        };
        model.addAttribute(EVENTS, events);
        return "search";
    }
}

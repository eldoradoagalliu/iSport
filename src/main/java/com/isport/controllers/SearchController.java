package com.isport.controllers;

import com.isport.models.Event;
import com.isport.models.User;
import com.isport.services.EventService;
import com.isport.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping("/search")
    public String search(Principal principal, Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        User currentUser = userService.findUser(principal.getName());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("year", Year.now());
        model.addAttribute("events", eventService.getNonUserEvents(currentUser, new Date()));

        return "search";
    }

    @PostMapping("/search")
    public String searchBy(Principal principal, @RequestParam("search") String search,
                           @RequestParam("selectedOption") String selectedOption, Model model) {
        User currentUser = userService.findUser(principal.getName());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("year", Year.now());
        List<Event> events = null;

        if (selectedOption.equals("eventName")) {
            events = eventService.findByEventName(search, new Date());
        } else if (selectedOption.equals("locationName")) {
            events = eventService.findByLocation(search, new Date());
        } else if (selectedOption.equals("creator")) {
            events = eventService.findByCreatorName(search, new Date());
        }
        model.addAttribute("events", events);

        return "search";
    }
}

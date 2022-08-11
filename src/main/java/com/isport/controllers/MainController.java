package com.isport.controllers;

import com.isport.models.Event;
import com.isport.models.LoginUser;
import com.isport.models.Message;
import com.isport.models.User;
import com.isport.services.EventService;
import com.isport.services.MessageService;
import com.isport.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
                           HttpSession session) {
        userService.register(newUser, result);

        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index";
        } else {
            session.setAttribute("userId", newUser.getId());
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
                        HttpSession session) {
        User user = userService.login(newLogin, result);

        if (result.hasErrors()){
            model.addAttribute("newUser", new User());
            return "index";
        }
        else{
            session.setAttribute("userId", user.getId());
            return "redirect:/dashboard";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,HttpSession session){
            if(session.getAttribute("userId") == null){
            return "redirect:/logout";
        }
        User user = userService.findById((Long) session.getAttribute("userId"));
        Date todaysDate = new Date();
        List<Event> todaysEvents = eventService.getTodaysEvents(todaysDate);
        model.addAttribute("user", user);
        model.addAttribute("numberOfMessages", messageService.allMessages().size());
        model.addAttribute("todaysDate", dateFormatter(todaysDate));
        model.addAttribute("numberOfEvents",todaysEvents.size());
        model.addAttribute("todaysEvents", todaysEvents);
        model.addAttribute("joinedEvents", user.getEvents());
        return "dashboard";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("event") Event event, HttpSession session){
        if(session.getAttribute("userId") == null){
            return "redirect:/logout";
        }

        return "new_event";
    }

    @PostMapping("/new")
    public String addEvent(@Valid @ModelAttribute("event") Event event, BindingResult result,
                           Model model,
                           HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Long userId = (Long) session.getAttribute("userId");
        if(result.hasErrors()){
            model.addAttribute("event", event);
            return "new_event";
        }
        else{
            User creator = userService.findById(userId);

            event.getDate().setHours(23);
            event.getDate().setMinutes(59);

            Event newEvent = new Event(event.getEventName(), event.getLocation(), event.getLatitude(),
                    event.getLongitude(), event.getAttendees(), event.getDate(), timeFormatter(event.getTime()),
                    event.getDescription());
            newEvent.setCreator(creator);
            eventService.addEvent(newEvent);

            creator.getEventsCreated().add(newEvent);
            userService.updateUser(creator);
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/event/{id}")
    public String eventDetails(@ModelAttribute("message") Message message, @PathVariable("id") Long id, Model model,
                               HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Event event = eventService.findById(id);

        model.addAttribute("event", event);
        model.addAttribute("users", userService.getAttendedEvents(event));
        model.addAttribute("messages", messageService.eventMessages(id));
        return "event_details";
    }

    @PostMapping("/event/message/{id}")
    public String addMessage(@Valid @ModelAttribute("message") Message message, BindingResult result,
                             @PathVariable("id") Long eventId,
                             Model model, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Long userId = (Long) session.getAttribute("userId");
        Event event = eventService.findById(eventId);

        if(result.hasErrors()){
            model.addAttribute("event", event);
            model.addAttribute("messages", messageService.eventMessages(eventId));
            return "event_details";
        }
        else{
            Message newMessage = new Message(message.getContent());
            newMessage.setUser(userService.findById(userId));
            newMessage.setEvent(event);
            messageService.addMessage(newMessage);
            return "redirect:/event/" + eventId;
        }
    }

    @RequestMapping("/event/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long eventId, Model model, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);

        for(Message message : messageService.eventMessages(eventId)){
            messageService.deleteMessage(message);
        }
        eventService.deleteEvent(event);

        return "redirect:/dashboard";
    }

    @GetMapping("/search")
    public String search(Model model, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        User user = userService.findById((Long) session.getAttribute("userId"));
        model.addAttribute("events", eventService.getNonUserEvents(user));

        return "search";
    }

    @PostMapping("/search")
    public String searchBy(@RequestParam("search") String search, @RequestParam("selectedOption") String selectedOption,
                           Model model, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }

        if(selectedOption.equals("eventName")){
            return "redirect:/search/event/" + search;
        }
        else if(selectedOption.equals("locationName")){
            return "redirect:/search/location/" + search;
        }
        else if(selectedOption.equals("creator")){
            return "redirect:/search/creator/" + search;
        }
        else return "redirect:/search";
    }

    @RequestMapping("/event/join/{id}")
    public String joinEvent(@PathVariable("id") Long eventId, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);
        user.getEvents().add(event);
        userService.updateUser(user);

        return "redirect:/search";
    }

    @RequestMapping("/event/leave/{id}")
    public String leaveEvent(@PathVariable("id") Long eventId, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);
        user.getEvents().remove(event);
        userService.updateUser(user);

        return "redirect:/search";
    }

    @GetMapping("/search/event/{eventName}")
    public String searchByEventName(@PathVariable("eventName") String eventName, Model model, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Long id = (Long) session.getAttribute("userId");
        List<Event> events = eventService.getEventsByName(eventName);
        model.addAttribute("events", events);
        return "search_opt1";
    }

    @GetMapping("/search/location/{locationName}")
    public String searchByLocationName(@PathVariable("locationName") String locationName, Model model,
                                       HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        List<Event> events = eventService.getEventsByLocation(locationName);
        model.addAttribute("events", events);
        return "search_opt2";
    }

    @GetMapping("/search/creator/{creator}")
    public String searchByCreatorName(@PathVariable("creator") String creator, Model model, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        List<Event> events = eventService.getEventsByCreatorName(creator);
        model.addAttribute("events", events);
        return "search_opt3";
    }

    @GetMapping("/account/{id}")
    public String userDetails(@PathVariable("id") Long id, Model model, HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }
        Date todaysDate = new Date();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("futureEvents", eventService.getEventsAfterDate(todaysDate));
        return "user_details";
    }

//    @GetMapping("/account/edit/{id}")
//    public String editUser(@PathVariable("id") Long id, HttpSession session){
//        return "edit_user";
//    }

    private String dateFormatter(Date date){
        String[] suffixes = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                            "th", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                            "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "st" };
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d");
        int day = date.getDate();
        String todaysDate = simpleDateFormat.format(date) + suffixes[day];
        return todaysDate;
    }

    private String timeFormatter(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        String[] tempTime = time.split(":");
        Date newTime = new Date();
        newTime.setHours(Integer.parseInt(tempTime[0]));
        newTime.setMinutes(Integer.parseInt(tempTime[1]));
        String stringTime = simpleDateFormat.format(newTime);
        return stringTime;
    }
}
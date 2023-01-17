package com.isport.controllers;

import com.isport.models.Event;
import com.isport.models.Message;
import com.isport.models.User;
import com.isport.services.EventService;
import com.isport.services.MessageService;
import com.isport.services.UserService;
import com.isport.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EventService eventService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("/")
    public String index(Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "register";
        }
        model.addAttribute("user", user);

        if(user != null) {
            user.setLastLogin(new Date());
            userService.updateUser(user);
            // Admin will be redirected to the Admin Page
            if(user.getRoles().get(0).getName().contains("ROLE_ADMIN")){
                model.addAttribute("currentUser", userService.findByEmail(email));
                model.addAttribute("users", userService.getNonAdminUsers());
                model.addAttribute("events", eventService.allEvents());
                return "admin_dashboard";
            }
        }
        //Normal users will be redirected to the Event Dashboard
        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("user") User user){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request) {
        userValidator.validate(user, result);
        String password = user.getPassword();
        if(result.hasErrors()) {
            return "register";
        }
        // First user will be made Admin
        if(userService.allUsers().size() == 0){
            userService.newUser(user, "ROLE_ADMIN");
        }
        else{
            userService.newUser(user, "ROLE_USER");
        }
        authWithHttpServletRequest(request, user.getEmail(), password);
        return "redirect:/";
    }

    //An authentication method used for the sign in
    public void authWithHttpServletRequest(HttpServletRequest request, String email, String password) {
        try {
            request.login(email, password);
        } catch (ServletException e) {
            System.out.println("Error while login: " + e);
        }
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error,
                        @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error!=null) {
            model.addAttribute("errorMessage","Invalid Credentials, Please try again.");
        }
        if(logout!=null) {
            model.addAttribute("logoutMessage","Logout Successful!");
        }
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }

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

    @GetMapping("/account/{id}")
    public String userDetails(Principal principal, @PathVariable("id") Long id, Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        Date todaysDate = new Date();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("futureEvents", eventService.getEventsAfterDate(todaysDate));
        return "user_details";
    }

    @GetMapping("/account/edit/{id}")
    public String editUser(Principal principal, @PathVariable("id") Long userId, @ModelAttribute("user") User user,
                           Model model){
        String email = principal.getName();
        User currentUser = userService.findByEmail(email);
        if(currentUser == null){
            return "redirect:/";
        }
        User editedUser = userService.findById(userId);
        model.addAttribute("user", editedUser);
        return "edit_user";
    }

    @RequestMapping("/account/edit/{id}")
    public String editUser(Principal principal, @PathVariable("id") Long userId,
                           @Valid @ModelAttribute("user") User editedUser, BindingResult result){
        String email = principal.getName();
        User currentUser = userService.findByEmail(email);
        if(currentUser == null){
            return "redirect:/";
        }
        if(result.hasErrors()){
            return "edit_user";
        }
        else {
            User user = userService.findById(userId);
            editedUser.setPhotoURL(user.getPhotoURL());
            editedUser.setEvents(user.getEvents());
            editedUser.setEventsCreated(user.getEventsCreated());
            editedUser.setMessages(user.getMessages());
            editedUser.setRoles(user.getRoles());
            editedUser.setCreatedAt(user.getCreatedAt());
            userService.updateUser(editedUser);
        }
        return "redirect:/";
    }

    @RequestMapping("/account/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId){
        User user = userService.findById(userId);
        for(Message message : user.getMessages()){
            messageService.deleteMessage(message);
        }
        for(int i = 0; i < user.getEventsCreated().size(); i++){
            user.getEventsCreated().remove(i);
        }
        //To Fix remove methods
        for(int i = 0; i < user.getEvents().size(); i++){
            user.getEvents().remove(i);
        }
        userService.deleteUser(user);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String add(Principal principal, @ModelAttribute("event") Event event, Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "new_event";
    }

    @PostMapping("/new")
    public String addEvent(Principal principal, @Valid @ModelAttribute("event") Event event, BindingResult result,
                           Model model){
        String email = principal.getName();
        User creator = userService.findByEmail(email);
        if(creator == null){
            return "redirect:/";
        }
        if(result.hasErrors()){
            model.addAttribute("event", event);
            return "new_event";
        }
        else{
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
    public String eventDetails(Principal principal, @ModelAttribute("message") Message message, @PathVariable("id") Long id,
                               Model model){
        String email = principal.getName();
        User creator = userService.findByEmail(email);
        if(creator == null){
            return "redirect:/";
        }
        model.addAttribute("user", creator);
        Event event = eventService.findById(id);

        model.addAttribute("event", event);
        model.addAttribute("users", userService.getAttendedEvents(event));
        model.addAttribute("messages", messageService.eventMessages(id));
        return "event_details";
    }

    @GetMapping("/event/edit/{id}")
    public String editEvent(Principal principal, @PathVariable("id") Long eventId, @ModelAttribute("event") Event event,
                            Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        Event editedEvent = eventService.findById(eventId);
        model.addAttribute("event", editedEvent);
        return "edit_event";
    }

    @RequestMapping("/event/edit/{id}")
    public String editEvent(Principal principal, @PathVariable("id") Long eventId,
                            @Valid @ModelAttribute("event") Event editedEvent, BindingResult result){
        String email = principal.getName();
        User currentUser = userService.findByEmail(email);
        if(currentUser == null){
            return "redirect:/";
        }
        if(result.hasErrors()){
            return "edit_event";
        }
        else {
            Event event = eventService.findById(eventId);
            editedEvent.setCreator(event.getCreator());
            editedEvent.setUsers(event.getUsers());
            editedEvent.setMessages(event.getMessages());
            editedEvent.setCreatedAt(event.getCreatedAt());
            eventService.updateEvent(editedEvent);
        }
        return "redirect:/";
    }

    @RequestMapping("/event/delete/{id}")
    public String deleteEvent(Principal principal, @PathVariable("id") Long eventId){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        Event event = eventService.findById(eventId);
        List<Message> messages = messageService.eventMessages(eventId);

        for(Message message : messages){
            messageService.deleteMessage(message);
        }
        eventService.deleteEvent(event);
        if(user.getRoles().get(0).getName().contains("ROLE_ADMIN")){
            return "redirect:/";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/event/message/{id}")
    public String addMessage(Principal principal, @Valid @ModelAttribute("message") Message message, BindingResult result,
                             @PathVariable("id") Long eventId, Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        Event event = eventService.findById(eventId);

        if(result.hasErrors()){
            model.addAttribute("event", event);
            model.addAttribute("messages", messageService.eventMessages(eventId));
            return "event_details";
        }
        else{
            Message newMessage = new Message(message.getContent());
            newMessage.setUser(user);
            newMessage.setEvent(event);
            messageService.addMessage(newMessage);
            return "redirect:/event/" + eventId;
        }
    }

    @GetMapping("/search")
    public String search(Principal principal, Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("events", eventService.getNonUserEvents(user));
        return "search";
    }

    @PostMapping("/search")
    public String searchBy(Principal principal, @RequestParam("search") String search,
                           @RequestParam("selectedOption") String selectedOption){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
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
    public String joinEvent(Principal principal, @PathVariable("id") Long eventId){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        Event event = eventService.findById(eventId);
        user.getEvents().add(event);
        userService.updateUser(user);
        return "redirect:/search";
    }

    @RequestMapping("/event/leave/{id}")
    public String leaveEvent(Principal principal, @PathVariable("id") Long eventId){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        Event event = eventService.findById(eventId);
        user.getEvents().remove(event);
        userService.updateUser(user);
        return "redirect:/search";
    }

    @GetMapping("/search/event/{eventName}")
    public String searchByEventName(Principal principal, @PathVariable("eventName") String eventName, Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        List<Event> events = eventService.getEventsByName(eventName);
        model.addAttribute("events", events);
        return "search_opt1";
    }

    @GetMapping("/search/location/{locationName}")
    public String searchByLocationName(Principal principal, @PathVariable("locationName") String locationName,
                                       Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        List<Event> events = eventService.getEventsByLocation(locationName);
        model.addAttribute("events", events);
        return "search_opt2";
    }

    @GetMapping("/search/creator/{creator}")
    public String searchByCreatorName(Principal principal, @PathVariable("creator") String creator, Model model){
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("user", user);
        List<Event> events = eventService.getEventsByCreatorName(creator);
        model.addAttribute("events", events);
        return "search_opt3";
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
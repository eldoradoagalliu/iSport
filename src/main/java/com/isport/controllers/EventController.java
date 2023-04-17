package com.isport.controllers;

import com.isport.models.Event;
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

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    final String REQUIRED_EVENT_DATETIME = "Event Date and Time are required!";

    final String FUTURE_EVENT_DATETIME = "The event date and time must be a date in the future!";

    @GetMapping("/new")
    public String add(Principal principal, @ModelAttribute("event") Event event, Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        model.addAttribute("currentUser", userService.findUser(principal.getName()));
        model.addAttribute("year", Year.now());

        return "new_event";
    }

    @PostMapping("/new")
    public String addEvent(Principal principal, @Valid @ModelAttribute("event") Event event, BindingResult result,
                           @RequestParam(value = "time") String time,
                           @RequestParam(value = "date") String date, Model model) {
        User creator = userService.findUser(principal.getName());

        if (result.hasErrors() || Objects.isNull(getDateFromString(date, time)) || getDateFromString(date, time).before(new Date())) {
            if (Objects.isNull(getDateFromString(date, time))) {
                model.addAttribute("nullDateTimeErrorMessage", REQUIRED_EVENT_DATETIME);
            } else {
                if (getDateFromString(date, time).before(new Date())) {
                    model.addAttribute("dateTimeErrorMessage", FUTURE_EVENT_DATETIME);
                }
            }
            model.addAttribute("currentUser", creator);
            model.addAttribute("year", Year.now());
            model.addAttribute("time", time);
            model.addAttribute("date", date);

            return "new_event";
        } else {
            Event newEvent = new Event(event.getEventName(), event.getLocation(), event.getLatitude(), event.getLongitude(),
                    event.getAttendees(), getDateFromString(date, time), event.getDescription(), creator);
            eventService.saveEvent(newEvent);

            return "redirect:/dashboard";
        }
    }

    @GetMapping("/event/{id}")
    public String eventDetails(Principal principal, @ModelAttribute("message") Message message, @PathVariable("id") Long eventId,
                               Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        model.addAttribute("currentUser", userService.findUser(principal.getName()));
        model.addAttribute("year", Year.now());
        Event event = eventService.findEvent(eventId);
        model.addAttribute("event", event);
        model.addAttribute("users", userService.getEventAttendees(event));
        model.addAttribute("messages", messageService.eventMessages(eventId));

        return "event_details";
    }

    @GetMapping("/event/edit/{id}")
    public String editEvent(Principal principal, @PathVariable("id") Long eventId, @ModelAttribute("event") Event event,
                            Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        model.addAttribute("currentUser", userService.findUser(principal.getName()));
        model.addAttribute("year", Year.now());
        Event editedEvent = eventService.findEvent(eventId);
        model.addAttribute("event", editedEvent);
        model.addAttribute("time", editedEvent.getTime());
        model.addAttribute("date", editedEvent.getDate());

        return "edit_event";
    }

    @PutMapping("/event/edit/{id}")
    public String editEvent(Principal principal, @PathVariable("id") Long eventId, @Valid @ModelAttribute("event") Event editedEvent,
                            BindingResult result, @RequestParam(value = "time") String time,
                            @RequestParam(value = "date") String date, Model model) {
        if (result.hasErrors() || Objects.isNull(getDateFromString(date, time)) || getDateFromString(date, time).before(new Date())) {
            if (Objects.isNull(getDateFromString(date, time))) {
                model.addAttribute("nullDateTimeErrorMessage", REQUIRED_EVENT_DATETIME);
            } else {
                if (getDateFromString(date, time).before(new Date())) {
                    model.addAttribute("dateTimeErrorMessage", FUTURE_EVENT_DATETIME);
                }
            }
            model.addAttribute("year", Year.now());
            model.addAttribute("currentUser", userService.findUser(principal.getName()));
            model.addAttribute("time", time);
            model.addAttribute("date", date);

            return "edit_event";
        } else {
            Event currentEvent = eventService.findEvent(eventId);
            editedEvent.setEventDateTime(getDateFromString(date, time));
            editedEvent.setCreator(currentEvent.getCreator());
            editedEvent.setUsers(currentEvent.getUsers());
            eventService.saveEvent(editedEvent);

            return "redirect:/event/{id}";
        }
    }

    @DeleteMapping("/event/delete/{id}")
    public String deleteEvent(Principal principal, @PathVariable("id") Long eventId) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        User currentUser = userService.findUser(principal.getName());
        Event event = eventService.findEvent(eventId);
        eventService.deleteEvent(event);

        if (currentUser.getRoles().get(0).getName().contains("ADMIN")) {
            return "redirect:/";
        }

        return "redirect:/dashboard";
    }

    @RequestMapping("/event/join/{id}")
    public String joinEvent(Principal principal, @PathVariable("id") Long eventId) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        User currentUser = userService.findUser(principal.getName());
        Event event = eventService.findEvent(eventId);
        currentUser.getEvents().add(event);
        userService.saveUser(currentUser);

        return "redirect:/event/" + eventId;
    }

    @RequestMapping("/event/leave/{id}")
    public String leaveEvent(Principal principal, @PathVariable("id") Long eventId) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        User currentUser = userService.findUser(principal.getName());
        Event event = eventService.findEvent(eventId);
        List<Message> userEventMessages = messageService.getUserEventMessages(currentUser.getId(), eventId);

        if (userEventMessages != null) {
            for (Message message : userEventMessages) {
                messageService.deleteMessage(message);
            }
        }
        currentUser.getEvents().remove(event);
        userService.saveUser(currentUser);

        return "redirect:/search";
    }

    @PostMapping("/event/message/{id}")
    public String addMessage(Principal principal, @Valid @ModelAttribute("message") Message message, BindingResult result,
                             @PathVariable("id") Long eventId, Model model) {
        User currentUser = userService.findUser(principal.getName());
        Event event = eventService.findEvent(eventId);

        if (result.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("year", Year.now());
            model.addAttribute("event", event);
            model.addAttribute("users", userService.getEventAttendees(event));
            model.addAttribute("messages", messageService.eventMessages(eventId));

            return "event_details";
        } else {
            Message newMessage = new Message(message.getContent());
            newMessage.setUser(currentUser);
            newMessage.setEvent(event);
            messageService.addMessage(newMessage);

            return "redirect:/event/{id}";
        }
    }

    public Date getDateFromString(String date, String time) {
        String dateTimeString = date + " " + time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date parsed = null;

        if (Objects.nonNull(date) && Objects.nonNull(time) && !dateTimeString.equals(date + " ") && !dateTimeString.equals(" " + time)) {
            try {
                parsed = format.parse(dateTimeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new Date(parsed.getTime());
        }

        return parsed;
    }
}

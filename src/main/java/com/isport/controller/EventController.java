package com.isport.controller;

import com.isport.model.Event;
import com.isport.model.Message;
import com.isport.model.User;
import com.isport.service.EventService;
import com.isport.service.MessageService;
import com.isport.service.UserService;
import com.isport.util.DateFormatter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.isport.constant.ISportConstants.*;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/event")
    public String addEvent(Principal principal, @ModelAttribute(EVENT) Event event, Model model) {
        model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
        model.addAttribute(YEAR, Year.now());
        return "new_event";
    }

    @PostMapping("/event")
    public String addEvent(Principal principal, @Valid @ModelAttribute(EVENT) Event event, BindingResult result,
                           @RequestParam(value = TIME) String time, @RequestParam(value = DATE) String date, Model model) {
        User creator = userService.findUser(principal.getName());

        if (result.hasErrors() || Objects.isNull(DateFormatter.getDateFromString(date, time)) ||
                DateFormatter.getDateFromString(date, time).before(new Date())) {
            if (Objects.isNull(DateFormatter.getDateFromString(date, time))) {
                model.addAttribute("nullDateTimeErrorMessage", REQUIRED_EVENT_DATETIME);
            } else {
                if (DateFormatter.getDateFromString(date, time).before(new Date())) {
                    model.addAttribute("dateTimeErrorMessage", FUTURE_EVENT_DATETIME);
                }
            }
            model.addAttribute(CURRENT_USER, creator);
            model.addAttribute(YEAR, Year.now());
            model.addAttribute(TIME, time);
            model.addAttribute(DATE, date);
            return "new_event";
        } else {
            Event newEvent = Event.builder()
                    .eventName(event.getEventName())
                    .location(event.getLocation())
                    .latitude(event.getLatitude())
                    .longitude(event.getLongitude())
                    .attendees(event.getAttendees())
                    .eventDateTime(DateFormatter.getDateFromString(date, time))
                    .description(event.getDescription())
                    .creator(creator)
                    .build();
            eventService.saveEvent(newEvent);
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/event/{id}")
    public String eventDetails(Principal principal, @ModelAttribute(MESSAGE) Message message, @PathVariable(ID) Long eventId,
                               Model model) {
        model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
        model.addAttribute(YEAR, Year.now());
        Event event = eventService.findEvent(eventId);
        model.addAttribute(EVENT, event);
        model.addAttribute(USERS, userService.getEventAttendees(event));
        model.addAttribute(MESSAGES, messageService.eventMessages(eventId));
        return "event_details";
    }

    @GetMapping("/event/{id}/edit")
    public String editEvent(Principal principal, @PathVariable(ID) Long eventId, @ModelAttribute(EVENT) Event event,
                            Model model) {
        model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
        model.addAttribute(YEAR, Year.now());
        Event editedEvent = eventService.findEvent(eventId);
        model.addAttribute(EVENT, editedEvent);
        model.addAttribute(TIME, editedEvent.getFormattedTime());
        model.addAttribute(DATE, editedEvent.getFormattedDate());
        return "edit_event";
    }

    @PutMapping("/event/{id}")
    public String editEvent(Principal principal, @PathVariable(ID) Long eventId, @Valid @ModelAttribute(EVENT) Event editedEvent,
                            BindingResult result, @RequestParam(value = TIME) String time,
                            @RequestParam(value = DATE) String date, Model model) {
        if (result.hasErrors() || Objects.isNull(DateFormatter.getDateFromString(date, time)) ||
                DateFormatter.getDateFromString(date, time).before(new Date())) {
            if (Objects.isNull(DateFormatter.getDateFromString(date, time))) {
                model.addAttribute("nullDateTimeErrorMessage", REQUIRED_EVENT_DATETIME);
            } else {
                if (DateFormatter.getDateFromString(date, time).before(new Date())) {
                    model.addAttribute("dateTimeErrorMessage", FUTURE_EVENT_DATETIME);
                }
            }
            model.addAttribute(YEAR, Year.now());
            model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
            model.addAttribute(TIME, time);
            model.addAttribute(DATE, date);
            return "edit_event";
        } else {
            Event currentEvent = eventService.findEvent(eventId);
            editedEvent.setEventDateTime(DateFormatter.getDateFromString(date, time));
            editedEvent.setCreator(currentEvent.getCreator());
            editedEvent.setUsers(currentEvent.getUsers());
            eventService.saveEvent(editedEvent);

            if (userService.isAdmin(principal)) {
                return "redirect:/";
            } else {
                return "redirect:/event/{id}";
            }
        }
    }

    @DeleteMapping("/event/{id}")
    public String deleteEvent(@PathVariable(ID) Long eventId) {
        Event event = eventService.findEvent(eventId);
        eventService.deleteEvent(event);
        return "redirect:/";
    }

    @GetMapping("/event/{id}/join")
    public String joinEvent(Principal principal, @PathVariable(ID) Long eventId) {
        User currentUser = userService.findUser(principal.getName());
        Event event = eventService.findEvent(eventId);
        currentUser.getEvents().add(event);
        userService.updateUser(currentUser);
        return "redirect:/event/{id}";
    }

    @GetMapping("/event/{id}/leave")
    public String leaveEvent(Principal principal, @PathVariable(ID) Long eventId) {
        User currentUser = userService.findUser(principal.getName());
        Event event = eventService.findEvent(eventId);
        List<Message> userEventMessages = messageService.getUserEventMessages(currentUser.getId(), eventId);

        if (userEventMessages != null) {
            userEventMessages.forEach(messageService::deleteMessage);
        }
        currentUser.getEvents().remove(event);
        userService.updateUser(currentUser);

        return "redirect:/search";
    }

    @PostMapping("/event/{id}/message")
    public String addMessage(Principal principal, @Valid @ModelAttribute(MESSAGE) Message message, BindingResult result,
                             @PathVariable(ID) Long eventId, Model model) {
        User currentUser = userService.findUser(principal.getName());
        Event event = eventService.findEvent(eventId);

        if (result.hasErrors()) {
            model.addAttribute(CURRENT_USER, currentUser);
            model.addAttribute(YEAR, Year.now());
            model.addAttribute(EVENT, event);
            model.addAttribute(USERS, userService.getEventAttendees(event));
            model.addAttribute(MESSAGES, messageService.eventMessages(eventId));
            return "event_details";
        } else {
            Message newMessage = Message.builder().content(message.getContent()).build();
            newMessage.setUser(currentUser);
            newMessage.setEvent(event);
            messageService.addMessage(newMessage);
            return "redirect:/event/{id}";
        }
    }
}

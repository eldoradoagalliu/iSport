package com.isport.controllers;

import com.isport.models.Event;
import com.isport.models.User;
import com.isport.services.EventService;
import com.isport.services.MessageService;
import com.isport.services.UserService;
import com.isport.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public String home(Principal principal, Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        User currentUser = userService.findUser(principal.getName());
        currentUser.setLastLogin(new Date());
        userService.saveUser(currentUser);

        if (userService.isAdmin(principal)) {
            model.addAttribute("year", Year.now());
            model.addAttribute("users", userService.getNonAdminUsers());
            model.addAttribute("events", eventService.getAllEvents());
            return "admin_dashboard";
        } else {
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("year", Year.now());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request,
                               Model model) {
        userValidator.validate(user, result);
        String password = user.getPassword();
        if (result.hasErrors() || Objects.nonNull(userService.findUser(user.getEmail()))) {
            if (Objects.nonNull(userService.findUser(user.getEmail()))) {
                model.addAttribute("emailExistsErrorMessage", "This email has been used by another user!");
            }
            return "register";
        }

        if (userService.getAllUsers().isEmpty()) {
            userService.createUser(user, "ADMIN");
        } else {
            userService.createUser(user, "USER");
        }
        authWithHttpServletRequest(request, user.getEmail(), password);

        return "redirect:/";
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String email, String password) {
        try {
            request.login(email, password);
        } catch (ServletException e) {
            System.out.println("Error while login: " + e);
        }
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute("user") User user, @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        model.addAttribute("year", Year.now());

        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        model.addAttribute("year", Year.now());
        User currentUser = userService.findUser(principal.getName());
        Date todayDate = new Date();
        List<Event> todayEvents = eventService.getTodayEvents(todayDate);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("numberOfMessages", messageService.getAllMessages().size());
        model.addAttribute("todayDate", dateFormatter(todayDate));
        model.addAttribute("numberOfEvents", todayEvents.size());
        model.addAttribute("todayEvents", todayEvents);
        model.addAttribute("joinedEvents", currentUser.getEvents());

        return "dashboard";
    }

    private String dateFormatter(Date date) {
        String[] suffixes = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                "th", "th", "th", "th", "th", "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "st"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d");
        int day = date.getDate();
        return simpleDateFormat.format(date) + suffixes[day];
    }
}

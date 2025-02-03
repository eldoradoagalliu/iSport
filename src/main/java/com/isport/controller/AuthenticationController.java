package com.isport.controller;

import com.isport.enums.UserRole;
import com.isport.model.Event;
import com.isport.model.User;
import com.isport.service.EventService;
import com.isport.service.MessageService;
import com.isport.service.UserService;
import com.isport.util.DateFormatter;
import com.isport.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.Year;
import java.util.Date;
import java.util.List;

import static com.isport.constant.ISportConstants.CURRENT_USER;
import static com.isport.constant.ISportConstants.EMAIL_USED;
import static com.isport.constant.ISportConstants.EVENTS;
import static com.isport.constant.ISportConstants.INVALID_CREDENTIALS;
import static com.isport.constant.ISportConstants.SUCCESSFUL_LOGOUT;
import static com.isport.constant.ISportConstants.USER;
import static com.isport.constant.ISportConstants.USERS;
import static com.isport.constant.ISportConstants.YEAR;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final EventService eventService;
    private final MessageService messageService;

    @GetMapping("/login")
    public String login(@ModelAttribute(USER) User user, @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", INVALID_CREDENTIALS);
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", SUCCESSFUL_LOGOUT);
        }
        model.addAttribute(YEAR, Year.now());
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute(USER) User user, Model model) {
        model.addAttribute(YEAR, Year.now());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute(USER) User user, BindingResult result, HttpServletRequest request,
                               Model model) {
        userValidator.validate(user, result);
        String password = user.getPassword();
        if (result.hasErrors() || userService.findUserByEmail(user.getEmail()).isPresent()) {
            if (userService.findUserByEmail(user.getEmail()).isPresent()) {
                model.addAttribute("emailExistsErrorMessage", EMAIL_USED);
                model.addAttribute(YEAR, Year.now());
            }
            return "register";
        }

        if (userService.getAllUsers().isEmpty()) {
            userService.registerUser(user, UserRole.ADMIN.getRole());
        } else {
            userService.registerUser(user, UserRole.USER.getRole());
        }
        authWithHttpServletRequest(request, user.getEmail(), password);

        return "redirect:/";
    }

    private void authWithHttpServletRequest(HttpServletRequest request, String email, String password) {
        try {
            request.login(email, password);
        } catch (ServletException e) {
            System.out.println("Error during login!" + e);
        }
    }

    @GetMapping("/")
    public String startHomePage(Principal principal) {
        if (principal == null) {
            return "redirect:/logout";
        }

        User currentUser = userService.findUser(principal.getName());
        if (currentUser == null) {
            return "redirect:/login";
        }
        currentUser.setLastLogin(new Date());
        userService.updateUser(currentUser);

        if (currentUser.isAdmin()) {
            return "redirect:/admin";
        } else {
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/logout";
        }

        model.addAttribute(YEAR, Year.now());
        User currentUser = userService.findUser(principal.getName());
        if (currentUser == null) {
            return "redirect:/login";
        }

        Date todayDate = new Date();
        List<Event> todayEvents = eventService.getTodayEvents(todayDate);
        model.addAttribute(CURRENT_USER, currentUser);
        model.addAttribute("numberOfMessages", messageService.getAllMessages().size());
        model.addAttribute("todayDate", DateFormatter.formatDateWithSuffix(todayDate));
        model.addAttribute("numberOfEvents", todayEvents.size());
        model.addAttribute("todayEvents", todayEvents);
        model.addAttribute("joinedEvents", currentUser.getEvents());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute(YEAR, Year.now());
        model.addAttribute(USERS, userService.getNonAdminUsers());
        model.addAttribute(EVENTS, eventService.getAllEvents());
        return "admin_dashboard";
    }
}

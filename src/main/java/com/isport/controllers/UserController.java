package com.isport.controllers;

import com.isport.models.User;
import com.isport.services.EventService;
import com.isport.services.UserService;
import com.isport.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.Year;
import java.util.Date;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    private static final String REUSED_OLD_PASSWORD = "You are using the old password! Please, try a new one.";

    private static final String INCORRECT_OLD_PASSWORD = "Your old password is incorrect";

    private static final String SUCCESSFUL_PASSWORD_CHANGE = "Password changed successfully!";

    private static final String REQUIRED_PASSWORDS = "The old password and new password are required!";

    @GetMapping("/account/{id}")
    public String userDetails(Principal principal, @PathVariable("id") Long userId, Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        model.addAttribute("currentUser", userService.findUser(principal.getName()));
        model.addAttribute("user", userService.findUser(userId));
        model.addAttribute("year", Year.now());
        model.addAttribute("futureEvents", eventService.getEventsAfterTodayDate(new Date()));

        return "user_details";
    }

    @PostMapping("/uploadProfilePhoto/{id}")
    public String uploadProfilePicture(@RequestParam("photo") MultipartFile multipartFile,
                                       @PathVariable("id") Long userId) throws IOException {
        User currentUser = userService.findUser(userId);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        currentUser.setProfilePhoto(fileName);
        userService.saveUser(currentUser);

        String uploadDir = "profile-photos/" + currentUser.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/account/{id}";
    }

    @GetMapping("/account/edit/{id}")
    public String editUser(Principal principal, @PathVariable("id") Long userId, @ModelAttribute("user") User user,
                           Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        model.addAttribute("currentUser", userService.findUser(principal.getName()));
        model.addAttribute("user", userService.findUser(userId));
        model.addAttribute("year", Year.now());

        return "edit_user";
    }

    @PutMapping("/account/edit/{id}")
    public String editUser(Principal principal, @PathVariable("id") Long userId,
                           @Valid @ModelAttribute("user") User editedUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("currentUser", userService.findUser(principal.getName()));
            model.addAttribute("user", userService.findUser(userId));
            model.addAttribute("year", Year.now());

            return "edit_user";
        } else {
            User currentUser = userService.findUser(userId);
            editedUser.setPassword(currentUser.getPassword());
            editedUser.setProfilePhoto(currentUser.getProfilePhoto());
            editedUser.setLastLogin(currentUser.getLastLogin());
            editedUser.setRoles(currentUser.getRoles());
            editedUser.setEvents(currentUser.getEvents());
            editedUser.setEventsCreated(currentUser.getEventsCreated());
            editedUser.setMessages(currentUser.getMessages());
            userService.saveUser(editedUser);
        }

        if (userService.isAdmin(principal)) {
            return "redirect:/";
        }

        return "redirect:/account/{id}";
    }

    @PostMapping("/account/changePassword")
    public String changeAccountPassword(Principal principal, @RequestParam("userId") Long userId,
                                        @RequestParam("oldPassword") String oldPassword,
                                        @RequestParam("newPassword") String newPassword, Model model) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
            Byte response = userService.changeUserPassword(userId, oldPassword, newPassword);
            if (response == 0) {
                model.addAttribute("incorrectOldPasswordMessage", INCORRECT_OLD_PASSWORD);
            } else if (response == 1) {
                model.addAttribute("oldPasswordReuseMessage", REUSED_OLD_PASSWORD);
            } else {
                model.addAttribute("successfulPasswordChangeMessage", SUCCESSFUL_PASSWORD_CHANGE);
            }
        } else {
            model.addAttribute("emptyPasswordErrorMessage", REQUIRED_PASSWORDS);
        }


        model.addAttribute("currentUser", userService.findUser(principal.getName()));
        model.addAttribute("user", userService.findUser(userId));
        model.addAttribute("year", Year.now());
        model.addAttribute("futureEvents", eventService.getEventsAfterTodayDate(new Date()));

        return "user_details";
    }

    @DeleteMapping("/account/delete/{id}")
    public String deleteUser(Principal principal, @PathVariable("id") Long userId) {
        if (userService.principalIsNull(principal)) return "redirect:/logout";

        User currentUser = userService.findUser(userId);
        userService.deleteUser(currentUser);

        return "redirect:/";
    }
}

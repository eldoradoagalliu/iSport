package com.isport.controller;

import com.isport.model.User;
import com.isport.service.EventService;
import com.isport.service.UserService;
import com.isport.util.FileUploadUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.io.IOException;
import java.security.Principal;
import java.time.Year;
import java.util.Date;
import java.util.Objects;

import static com.isport.constant.ISportConstants.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    @GetMapping("/account/{id}")
    public String accountDetails(Principal principal, @PathVariable(ID) Long userId, Model model) {
        model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
        model.addAttribute(USER, userService.findUser(userId));
        model.addAttribute(YEAR, Year.now());
        model.addAttribute(FUTURE_EVENTS, eventService.getEventsAfterTodayDate(new Date()));
        return "account_details";
    }

    @PostMapping("/uploadProfilePhoto/{id}")
    public String uploadProfilePicture(@RequestParam("photo") MultipartFile multipartFile,
                                       @PathVariable(ID) Long userId) throws IOException {
        User currentUser = userService.findUser(userId);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        currentUser.setProfilePhoto(fileName);
        userService.updateUser(currentUser);

        String uploadDir = "profile-photos/" + currentUser.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/account/{id}";
    }

    @GetMapping("/account/{id}/edit")
    public String editUser(Principal principal, @PathVariable(ID) Long userId, @ModelAttribute(USER) User user,
                           Model model) {
        model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
        model.addAttribute(USER, userService.findUser(userId));
        model.addAttribute(YEAR, Year.now());
        return "edit_user";
    }

    @PutMapping("/account/{id}")
    public String editUser(Principal principal, @PathVariable(ID) Long userId,
                           @Valid @ModelAttribute(USER) User editedUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
            model.addAttribute(USER, userService.findUser(userId));
            model.addAttribute(YEAR, Year.now());
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
            userService.updateUser(editedUser);
        }

        if (userService.isAdmin(principal)) {
            return "redirect:/";
        } else {
            return "redirect:/account/{id}";
        }
    }

    @PostMapping("/account/change/password")
    public String changeAccountPassword(Principal principal, @RequestParam("userId") Long userId,
                                        @RequestParam("oldPassword") String oldPassword,
                                        @RequestParam("newPassword") String newPassword, Model model) {
        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
            byte response = userService.changeUserPassword(userId, oldPassword, newPassword);
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

        model.addAttribute(CURRENT_USER, userService.findUser(principal.getName()));
        model.addAttribute(USER, userService.findUser(userId));
        model.addAttribute(YEAR, Year.now());
        model.addAttribute(FUTURE_EVENTS, eventService.getEventsAfterTodayDate(new Date()));

        return "account_details";
    }

    @DeleteMapping("/account/{id}")
    public String deleteUser(@PathVariable(ID) Long userId) {
        User currentUser = userService.findUser(userId);
        userService.deleteUser(currentUser);
        return "redirect:/";
    }
}

package com.isport.services;

import com.isport.models.Event;
import com.isport.models.User;
import com.isport.repositories.RoleRepository;
import com.isport.repositories.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder bCryptPwEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder bCryptPwEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.bCryptPwEncoder = bCryptPwEncoder;
    }

    public void createUser(User user, String role) {
        user.setPassword(bCryptPwEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByName(role));
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User findUser(Long id) {
        return userRepo.findByIdIs(id);
    }

    public User findUser(String email) {
        return userRepo.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void upgradeUser(User user) {
        user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
    }

    public Byte changeUserPassword(Long userId, String oldPassword, String newPassword) {
        User currentUser = findUser(userId);
        String encodedNewPassword = bCryptPwEncoder.encode(newPassword);

        if(bCryptPwEncoder.matches(oldPassword, currentUser.getPassword())) {
            if(bCryptPwEncoder.matches(newPassword, currentUser.getPassword())) {
                return 1;
            } else {
                currentUser.setPassword(encodedNewPassword);
                userRepo.save(currentUser);
                return 2;
            }
        } else {
            return 0;
        }
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public List<User> getEventAttendees(Event event) {
        return userRepo.findAllByEvents(event);
    }

    public List<User> getNonEventAttendees(Event event) {
        return userRepo.findByEventsNotContains(event);
    }

    public List<User> getNonAdminUsers() {
        return userRepo.getNonAdminUsers();
    }

    public Boolean principalIsNull(Principal principal) {
        return Objects.isNull(findUser(principal.getName()));
    }
}

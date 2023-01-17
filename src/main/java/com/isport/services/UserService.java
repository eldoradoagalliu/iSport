package com.isport.services;

import com.isport.models.Event;
import com.isport.models.User;
import com.isport.repositories.RoleRepository;
import com.isport.repositories.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void newUser(User user, String role) {
        user.setPassword(bCryptPwEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByName(role));
        userRepo.save(user);
    }

    public List<User> allUsers() {
        return userRepo.findAll();
    }

    public User findById(Long id) {
        return userRepo.findByIdIs(id);
    }
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    public User upgradeUser(User user) {
        user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
        return userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public List<User> getAttendedEvents(Event event){
        return userRepo.findAllByEvents(event);
    }

    public List<User> getUnAttendedEvents(Event event){
        return userRepo.findByEventsNotContains(event);
    }

//    public List<User> getAllNonAdminUsers(){
//        List<User> nonAdminUsers = new ArrayList<>();
//        List<User> users = allUsers();
//        for (User user : users){
//            if(user.getId() > 1){
//                nonAdminUsers.add(user);
//            }
//        }
//        return nonAdminUsers;
//    }
    public List<User> getNonAdminUsers(){
        return userRepo.getNonAdminUsers();
    }
}
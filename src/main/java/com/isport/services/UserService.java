package com.isport.services;

import com.isport.models.Event;
import com.isport.models.LoginUser;
import com.isport.models.User;
import com.isport.repositories.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User register(User newUser, BindingResult result) {
        Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());

        if(potentialUser.isPresent()) {
            result.rejectValue("email", "Matches", "An account with that email already exists!");
        }

        if(!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
        }

        if(result.hasErrors()) {
            return null;
        }

        String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashed);
        return userRepo.save(newUser);
    }

    public User login(LoginUser newLogin, BindingResult result) {
        Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());

        if(!potentialUser.isPresent()) {
            result.rejectValue("email", "Matches", "User not found!");
            return null;
        }
        User user = potentialUser.get();

        if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
            result.rejectValue("password", "Matches", "Invalid Password!");
        }

        if(result.hasErrors()) {
            return null;
        }
        return user;
    }

    public List<User> allUsers(){
        return userRepo.findAll();
    }

    public User findById(Long id){
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isPresent()) return optionalUser.get();
        else return null;
    }

    public User updateUser(User user){
        return userRepo.save(user);
    }

    public List<User> getAttendedEvents(Event event){
        return userRepo.findAllByEvents(event);
    }

    public List<User> getUnAttendedEvents(Event event){
        return userRepo.findByEventsNotContains(event);
    }

//    public boolean containsUser(Event event, Long userId){
//        List<User> users = event.getUsers();
//        for(User user : users){
//            if(user.getId() == userId) return true;
//        }
//        return false;
//    }
}
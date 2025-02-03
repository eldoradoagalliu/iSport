package com.isport.service;

import com.isport.enums.UserRole;
import com.isport.model.Event;
import com.isport.model.Role;
import com.isport.model.User;
import com.isport.repository.RoleRepository;
import com.isport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return userRepository.getReferenceById(id);
    }

    public User findUser(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void registerUser(User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName(role));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public byte changeUserPassword(Long userId, String oldPassword, String newPassword) {
        User currentUser = findUser(userId);
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            if (passwordEncoder.matches(newPassword, currentUser.getPassword())) {
                return 1;
            } else {
                currentUser.setPassword(encodedNewPassword);
                userRepository.save(currentUser);
                return 2;
            }
        } else {
            return 0;
        }
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public List<User> getEventAttendees(Event event) {
        return userRepository.findByEventsContains(event);
    }

    public List<User> getNonAdminUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.isAdmin())
                .collect(Collectors.toList());
    }

    public Boolean isAdmin(Principal principal) {
        User currentUser = findUser(principal.getName());
        return currentUser.getRoles().stream()
                .map(Role::getName)
                .anyMatch(role -> role.equals(UserRole.ADMIN.getRole()));
    }
}

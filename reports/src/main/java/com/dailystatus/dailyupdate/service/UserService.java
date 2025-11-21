package com.dailystatus.dailyupdate.service;

import com.dailystatus.dailyupdate.repository.UserRepository;
import com.dailystatus.dailyupdate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("USER");

        }
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        System.out.println("User found: " + user.getEmail());
        System.out.println("User details from DB: " + user);
        boolean isMatch = passwordEncoder.matches(rawPassword, user.getPassword());
        System.out.println("Password match result: " + isMatch);
        return isMatch;
    }



}

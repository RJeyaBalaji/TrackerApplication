package com.dailystatus.dailyupdate.controller;

import com.dailystatus.dailyupdate.entity.User;
import com.dailystatus.dailyupdate.service.JwtService;
import com.dailystatus.dailyupdate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        boolean isAuthenticated = userService.authenticate(user.getEmail(), user.getPassword());
        if (isAuthenticated) {
            User fullUser = userService.findByEmail(user.getEmail());

            System.out.println("User details : "+fullUser);
            if(fullUser.getRole() == null) {
                System.out.println("User role is null for user: " + fullUser.getRole() + " email : "+ user.getEmail());
            }else{
                System.out.println("User role is null for user: " + fullUser.getRole());
            }
            String token = jwtService.generateToken(fullUser.getEmail(), fullUser.getRole());
            return ResponseEntity.ok(new AuthResponse(token, user.getEmail()));
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    static class AuthResponse {
        private String token;
        private String email;

        public AuthResponse(String token, String email) {
            this.token = token;
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public String getEmail() {
            return email;
        }
    }
}

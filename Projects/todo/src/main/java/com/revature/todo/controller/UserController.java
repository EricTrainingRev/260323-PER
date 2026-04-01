package com.revature.todo.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import com.revature.todo.entity.User;
import com.revature.todo.exception.LoginFail;
import com.revature.todo.exception.RegistrationFail;
import com.revature.todo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws RegistrationFail {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody User user, HttpServletResponse response) throws LoginFail {
        String token = userService.login(user.getUsername(), user.getPassword());
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(15 * 60); // 15 minutes
        response.setHeader("Set-Cookie", String.format("token=%s; HttpOnly; Path=/; Max-Age=%d; SameSite=Strict", token, 15 * 60));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire immediately
        response.setHeader("Set-Cookie", "token=; HttpOnly; Path=/; Max-Age=0; SameSite=Strict");
        return ResponseEntity.noContent().build();
    }    

    @ExceptionHandler(RegistrationFail.class)
    public ResponseEntity<String> handleRegistrationFail(RegistrationFail e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(LoginFail.class)
    public ResponseEntity<String> handleLoginFail(LoginFail e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}

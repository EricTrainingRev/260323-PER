package com.revature.todo.service;

import org.springframework.stereotype.Service;
import com.revature.todo.entity.User;
import com.revature.todo.repo.UserRepository;
import com.revature.todo.utility.JWTUtility;
import com.revature.todo.data.TokenBearer;

import lombok.AllArgsConstructor;

import com.revature.todo.exception.LoginFail;
import com.revature.todo.exception.RegistrationFail;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User register(User user) throws RegistrationFail {
		if (userRepository.existsById(user.getUsername())) {
			throw new RegistrationFail("Username already exists");
		}
		User createdUser = userRepository.save(user);
		createdUser.setPassword(null);
		return createdUser;
	}

	public String login(String username, String password) throws LoginFail {
		User user = userRepository.findById(username)
			.filter(u -> u.getPassword().equals(password))
			.orElseThrow(() -> new LoginFail("Invalid credentials"));
		return JWTUtility.generateToken(user);
	}
}

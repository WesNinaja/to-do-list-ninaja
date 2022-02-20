package com.ninaja.todoapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.model.dto.UserCredentialsDTO;
import com.ninaja.todoapi.model.dto.UserLoginDTO;
import com.ninaja.todoapi.model.dto.UserRegisterDTO;
import com.ninaja.todoapi.repository.UserRepository;
import com.ninaja.todoapi.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class UserController {
	
	private @Autowired UserService userService;
	private @Autowired UserRepository repository;
	
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<User> createUser(@Valid @RequestBody UserRegisterDTO newUser){
		return userService.registerUser(newUser);
	}
	
	@GetMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	public List<User>getAllUsers(){
		return userService.listAllUsers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findByEmail(@PathVariable(value = "id") String id) {
		return ResponseEntity.ok().body(repository.findByEmail(id).get());
	}

	
	@PutMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser){
		return userService.updateUser(newUser);
	}
	
	@PutMapping("/login")
	public ResponseEntity<UserCredentialsDTO> credentials(@Valid @RequestBody UserLoginDTO user){
		return userService.getCredentials(user);
	}


}

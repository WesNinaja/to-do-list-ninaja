package com.ninaja.todoapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.model.dto.UserRegisterDTO;
import com.ninaja.todoapi.repository.UserRepository;
import com.ninaja.todoapi.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
public class UserController {

	private @Autowired UserService userService;
	private @Autowired UserRepository repository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<User> getAllUsers() {
		return userService.listAllUsers();
	}

	@GetMapping("/{id}")

	public ResponseEntity<User> findById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Optional<User>> findByEmail(@PathVariable String email) {
		return ResponseEntity.ok(repository.findByEmail(email));
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterDTO newUser, Errors errors) {
		return userService.registerUser(newUser, errors);
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {

		return userService.updateUser(user).map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

}
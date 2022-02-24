package com.ninaja.todoapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.ninaja.todoapi.exceptions.ModelError;
import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.model.dto.UserRegisterDTO;
import com.ninaja.todoapi.repository.UserRepository;
import com.ninaja.todoapi.security.JwtUtils;
import com.ninaja.todoapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired(required=true)
    AuthenticationManager authenticationManager;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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
	

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid name, email or password",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ModelError.class)) }
        ),
        @ApiResponse(
            responseCode = "422",
            description = "User already registered",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ModelError.class)) }
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
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
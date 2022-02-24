package com.ninaja.todoapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ninaja.todoapi.exceptions.ModelError;
import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.model.dto.CredentialsDTO;
import com.ninaja.todoapi.security.JwtUtils;
import com.ninaja.todoapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class LoginController {
	

	@Autowired(required=true)
    AuthenticationManager authenticationManager;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    
    @Operation(summary = "Get credentials")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Accredited user",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CredentialsDTO.class)) }
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Email or password field is wrong",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ModelError.class)) }
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credentials email or password invalid",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ModelError.class)) }
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
	@CrossOrigin
	@PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody CredentialsDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), UserService.encryptPassword(loginRequest.getSenha()) ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new CredentialsDTO(
                userDetails.getId(),
                userDetails.getNome(),
                userDetails.getEmail(),
                userDetails.getSenha(),
                jwt
        ));
    }

}

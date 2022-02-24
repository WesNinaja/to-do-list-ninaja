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

import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.model.dto.CredentialsDTO;
import com.ninaja.todoapi.security.JwtUtils;
import com.ninaja.todoapi.service.UserService;

@RestController
public class LoginController {
	

	@Autowired(required=true)
    AuthenticationManager authenticationManager;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

	
	
	@CrossOrigin
	@PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody CredentialsDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), UserService.encryptPassword(loginRequest.getSenha()) ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
       // UserDetailsImplements userDetails = (UserDetailsImplements) authentication.getPrincipal();
      /*  List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());*/
        return ResponseEntity.ok(new CredentialsDTO(
                userDetails.getId(),
                userDetails.getNome(),
                userDetails.getEmail(),
                userDetails.getSenha(),
                jwt
        ));
    }

}

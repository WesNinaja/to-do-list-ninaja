package com.ninaja.todoapi.exceptions.manipulators;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ninaja.todoapi.exceptions.ModelError;
import com.ninaja.todoapi.exceptions.models.ErrorInRegistrationException;
import com.ninaja.todoapi.exceptions.models.ExistingEmailException;
import com.ninaja.todoapi.exceptions.models.ValidLoginException;

@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(ExistingEmailException.class)
	public ResponseEntity<ModelError> existingEmailExceptionHandler(ExistingEmailException exception) {
		return ResponseEntity.status(422).body(new ModelError(422L, exception.getMessage(), "Try another email"));
	}

	@ExceptionHandler(ErrorInRegistrationException.class)
	public ResponseEntity<ModelError> errorInRegistrationExceptionHandler(ErrorInRegistrationException exception) {
		return ResponseEntity.status(400).body(new ModelError(400L, exception.getMessage(),
				"Maybe the password is not strong, it needs to be at least 6 digits long, with numbers, uppercase letters, lowercase letters, special characters and special characters."));
	}

	@ExceptionHandler(ValidLoginException.class)
	public ResponseEntity<ModelError> errorvalidInLoginExceptionHandler(ValidLoginException exception) {
		return ResponseEntity.status(400)
				.body(new ModelError(400L, exception.getMessage(), "Provide valid credentials"));
	}

}
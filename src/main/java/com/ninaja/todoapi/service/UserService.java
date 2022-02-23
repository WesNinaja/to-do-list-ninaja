package com.ninaja.todoapi.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.ninaja.todoapi.exceptions.models.ErrorInRegistrationException;
import com.ninaja.todoapi.exceptions.models.ExistingEmailException;
import com.ninaja.todoapi.helpers.PasswordValidate;
import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.model.dto.UserRegisterDTO;
import com.ninaja.todoapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private User user;

	public List<User> listAllUsers() {
		return userRepository.findAll();
	}

	public ResponseEntity<User> findUserById(Long id) {
		return userRepository.findById(id).map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	
	/**
	 * Public method used to register a user in the system's database. This method
	 * returns a BAD_REQUEST if the intention to register already has an email
	 * registered in the system, to avoid duplication. If you don't hear an existing
	 * email in the system, it returns CREATED status with user object in response.
	 * 
	 * @param newUser, UserRegisterDTO object.
	 * @return ResponseEntity<User>
	 * @author WesNinaja
	 * @since 1.0
	 * 
	 */
	public ResponseEntity<User> registerUser(@Valid UserRegisterDTO newUser, Errors errors) {
		if (errors.hasErrors()) {
			throw new ErrorInRegistrationException();
		}
		Optional<User> optional = userRepository.findByEmail(newUser.getEmail());
		if (optional.isPresent()) {
			throw new ExistingEmailException(newUser.getEmail());
		} else {
			user = new User();
			user.setNome(newUser.getNome());
			user.setEmail(newUser.getEmail());
			if (PasswordValidate.validator(newUser.getSenha())) {
				user.setSenha(encryptPassword(newUser.getSenha()));
				return ResponseEntity.status(201).body(userRepository.save(user));
			} else
				throw new ErrorInRegistrationException();
		}
	}

	
	/**
	 * Public method used to uptade a user in the system's database. 	 * 
	 * @param usuario, User object.
	 * @return ResponseEntity<User>
	 * @author WesNinaja
	 * @since 1.0
	 * 
	 */
	public Optional<User> updateUser(User usuario) {
		if (userRepository.findByEmail(usuario.getEmail()).isPresent()) {

			Optional<User> findUser = userRepository.findByEmail(usuario.getEmail());
			if (PasswordValidate.validator(usuario.getSenha())) {
				findUser.get().setSenha(encryptPassword(usuario.getSenha()));
				findUser.get().setEmail(usuario.getEmail());
				findUser.get().setNome(usuario.getNome());

				return Optional.ofNullable(userRepository.save(findUser.get()));
			} else
				throw new ErrorInRegistrationException();
		}

		return Optional.empty();

	}
	
	/**
	 * Private static method, used to encrypt with BCryptPasswordEncoder format a
	 * string passed as a parameter.
	 * 
	 * @param password, String format.
	 * @return String
	 * @author WesNinaja
	 * @since 1.0
	 * @see BCryptPasswordEncoder
	 * 
	 */
	private static String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

}
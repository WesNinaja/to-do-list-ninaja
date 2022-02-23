package com.ninaja.todoapi.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

	public ResponseEntity<User> registerUser(@Valid UserRegisterDTO newUser) {
		Optional<User> optional = userRepository.findByEmail(newUser.getEmail());
		if (optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Usuario ja existente, cadastre com outro email!");
		} else {
			user = new User();
			user.setNome(newUser.getNome());
			user.setEmail(newUser.getEmail());
			if (PasswordValidate.validator(newUser.getSenha())) {
				user.setSenha(encryptPassword(newUser.getSenha()));
				return ResponseEntity.status(201).body(userRepository.save(user));
			} else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Senha não é forte, precisa ter ter pelo 6 digitos, com números, letras minúsculas, maiúsculas e caracteres especiais.");
		}
	}

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

	public Optional<User> updateUser(User usuario) {
		if (userRepository.findByEmail(usuario.getEmail()).isPresent()) {

			Optional<User> findUser = userRepository.findByEmail(usuario.getEmail());
			if (PasswordValidate.validator(usuario.getSenha())) {
				findUser.get().setSenha(encryptPassword(usuario.getSenha()));
				findUser.get().setEmail(usuario.getEmail());
				findUser.get().setNome(usuario.getNome());

				return Optional.ofNullable(userRepository.save(findUser.get()));
			} else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Senha não é forte, precisa ter ter pelo 6 digitos, com números, letras minúsculas, maiúsculas e caracteres especiais.");
		}

		return Optional.empty();

	}

	private static String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

}
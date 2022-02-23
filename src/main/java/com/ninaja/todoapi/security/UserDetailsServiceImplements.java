package com.ninaja.todoapi.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.repository.UserRepository;

@Service
@Component
public class UserDetailsServiceImplements implements UserDetailsService {

	private @Autowired UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = repository.findByEmail(username);
		if (user.isPresent()) {
			return new UserDetailsImplements(user.get().getEmail(), user.get().getSenha());
		} else {
			throw new UsernameNotFoundException(username + " não existe");
		}
	}

}
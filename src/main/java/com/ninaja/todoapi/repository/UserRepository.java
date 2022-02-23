package com.ninaja.todoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninaja.todoapi.model.User;

/**
 * Inteface reponseble for inheriting crud methods
 * 
 * @author WesNinaja
 * @since 1.0
 * @see TaskRepository
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);
	
}

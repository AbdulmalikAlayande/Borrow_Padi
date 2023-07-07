package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.User;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<List<User>> findByUsernameAndPassword(String username, String password) throws ObjectDoesNotExistException;
	Optional<List<User>> findByUsername(String username) throws ObjectDoesNotExistException;
	boolean existsByEmailAndPassword(String email, String password);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<List<User>> findAllByEmail(String email);
	@Transactional void deleteByUsername(String username);
}

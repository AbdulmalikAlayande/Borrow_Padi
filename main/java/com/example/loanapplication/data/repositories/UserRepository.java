package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<List<User>> findByUsernameAndPassword(String email, String password);
	Optional<List<User>> findByUsername(String username);
	boolean existsByEmailAndPassword(String email, String password);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<List<User>> findAllByEmail(String email);
	@Transactional void deleteByUsername(String username);
}

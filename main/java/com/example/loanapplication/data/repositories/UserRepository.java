package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<List<User>> findByUsernameAndPassword(String email, String password);
}

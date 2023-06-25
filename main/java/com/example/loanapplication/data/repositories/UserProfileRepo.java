package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfile, String > {
	
	Optional<UserProfile> findByUsernameAndUserPin(String username, String password);
	void deleteByUsernameAndUserPin(String username, String pin);
}

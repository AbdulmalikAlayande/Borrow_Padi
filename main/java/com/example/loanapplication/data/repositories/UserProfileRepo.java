package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.User;
import com.example.loanapplication.data.models.UserProfile;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfile, String > {
	
	Optional<UserProfile> findByUsername(String username);
	void deleteByUsernameAndUserPin(String username, String pin);
	Optional<UserProfile> findByUser(@NonNull User user);
}

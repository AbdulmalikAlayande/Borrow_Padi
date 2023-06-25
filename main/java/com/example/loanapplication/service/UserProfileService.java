package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;

import java.util.Optional;

public interface UserProfileService {
	
	UserProfileResponse saveUserProfile(UserProfileRequest userProfileRequest);
	Optional<UserProfileResponse> findUserProfileByUsernameAndPassword(String username, String password);
	Optional<UserProfileResponse> findUserProfileByUsernameAndPin(String username, String pin);
	void deleteUserByUsernameAndPin(String username, String pin);
	Optional<UserProfileResponse> findUserById(String userId);
	void deleteUserProfileByUserId(String userId);
	
	long count();
	
	void deleteAll();
}

package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;

import java.util.Optional;

public interface UserProfileService {
	
	UserProfileResponse saveUserProfile(UserProfileRequest userProfileRequest);
	Optional<UserProfileResponse> findUserProfileByUsernameAndPassword(String username, String password);
	Optional<UserProfileResponse> findUserProfileByUsernameAndPin(String username, String pin);
}

package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;
import com.example.loanapplication.data.models.User;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;

import java.util.Optional;

public interface UserProfileService {
	
	UserProfileResponse saveUserProfile(UserProfileRequest userProfileRequest);
	Optional<UserProfileResponse> findUserProfileByUsername(String username) throws ObjectDoesNotExistException;
	void deleteUserByUsernameAndPin(String username, String pin);
	Optional<UserProfileResponse> findProfileById(String userId) throws ObjectDoesNotExistException;
	Optional<UserProfileResponse> findProfileByUser(User user);
	void deleteUserProfileByUserId(String userId);
	
	long count();
	void deleteAll();
}

package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;
import com.example.loanapplication.data.repositories.BankInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowPadiUserProfileService implements UserProfileService{
	
	@Autowired
	BankInfoRepo bankInfoRepo;
	
	@Override
	public UserProfileResponse saveUserProfile(UserProfileRequest userProfileRequest) {
		return null;
	}
	
	@Override
	public Optional<UserProfileResponse> findUserProfileByUsernameAndPassword(String username, String password) {
		return Optional.empty();
	}
	
	@Override
	public Optional<UserProfileResponse> findUserProfileByUsernameAndPin(String username, String pin) {
		return Optional.empty();
	}
}

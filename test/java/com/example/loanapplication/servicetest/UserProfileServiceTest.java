package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;
import com.example.loanapplication.service.UserProfileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserProfileServiceTest {
	
	@Autowired
	UserProfileService userProfileService;
	UserProfileRequest userProfileRequest;
	UserProfileResponse userProfileResponse;
	@BeforeEach
	void startAllTestWith() {
		userProfileRequest = buildUserProfileRequest();
		userProfileResponse = userProfileService.saveUserProfile(userProfileRequest);
	}
	
	private UserProfileRequest buildUserProfileRequest() {
		return null;
	}
	
	@AfterEach
	void endAllTestWith() {
	}
}
package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;
import com.example.loanapplication.data.models.User;
import com.example.loanapplication.data.repositories.UserRepository;
import com.example.loanapplication.exceptions.FieldCannotBeEmptyException;
import com.example.loanapplication.service.CustomerService;
import com.example.loanapplication.service.UserProfileService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserProfileServiceTest {
	
	@Autowired
	UserProfileService userProfileService;
	@Autowired
	UserRepository userRepository;
	UserProfileRequest userProfileRequest;
	UserProfileResponse userProfileResponse;
	@Autowired
	CustomerService customerService;
	User user;
	@SneakyThrows
	@BeforeEach void startAllTestWith() {
		userProfileService.deleteAll();
		userProfileRequest = buildUserProfileRequest3();
		userProfileResponse = userProfileService.saveUserProfile(userProfileRequest);
	}
	
	@AfterEach
	void endAllTestWith() {
//		userProfileService.deleteAll();
	}
	
	@Test void saveUserProfileTest(){
		assertEquals(BigInteger.ONE.longValue(), userProfileService.count());
		assertNotNull(userProfileResponse);
		assertNotNull(userProfileResponse.getMessage());
		assertTrue(userProfileResponse.isProfileSetUpState());
		assertEquals("Profile Set Successfully", userProfileResponse.getMessage());
	}
	
	@Test void testFieldCannotBeEmptyExceptionIsThrownWhenTheFieldsAreNullOrThereIsAnAttemptToMapANullValue(){
		assertThrowsExactly(FieldCannotBeEmptyException.class,
				()-> userProfileService.saveUserProfile(buildUserProfileRequest2()),
				"Exception is thrown because the fields or one of the fields is empty");
	}
	@SneakyThrows
	@Test void saveUserProfile_FindSavedProfileByIdTest(){
		Optional<UserProfileResponse> foundProfile = userProfileService.findProfileById(userProfileResponse.getProfileId());
		foundProfile.ifPresent(profileResponse -> {
			assertNotNull(profileResponse);
			System.out.println("aremu");
			assertNotNull(profileResponse.getProfileId());
		});
	}
	
//	@Disabled
	@Test void saveUserProfile_FindSavedProfileByUsernameAndPasswordTest(){
		Optional<UserProfileResponse> foundProfile = userProfileService.findProfileByUser(new User());
		foundProfile.ifPresent(profileResponse -> {
			System.out.println(profileResponse.getProfileId());
			assertNotNull(profileResponse);
			assertNotNull(profileResponse.getProfileId());
		});
	}
	@SneakyThrows
	@Test void saveUserProfile_FindSavedProfileByUsernameAndPinTest(){
		Optional<UserProfileResponse> foundProfile = userProfileService.findUserProfileByUsername(userProfileRequest.getUsername());
		foundProfile.ifPresent(profileResponse -> {
			assertNotNull(profileResponse);
			System.out.println("vado");
			assertNotNull(profileResponse.getProfileId());
		});
	}
	
	
	
	private UserProfileRequest buildUserProfileRequest() {
		return UserProfileRequest.builder()
				       .bvn("65438")
				       .bankName("Union Bank")
				       .accountNumber("202234784109")
				       .accountName("Oyingidi Sanusi")
				       .password("ayanniyi@20")
				       .city("Yaba")
				       .houseNumber("327")
				       .username("Niyi")
				       .state("Lagos")
				       .userPin("1990")
				       .postCode("1212")
				       .streetName("Herbert Macaulay")
				       .build();
	}
	
	private UserProfileRequest buildUserProfileRequest2() {
		return UserProfileRequest.builder().build();
	}
	
	private void buildUser() {
		user = User.builder().lastName("Alayande")
				       .firstName("Abdulmalik")
				       .email("alaabdulmalik03@gmail.com")
				       .phoneNumber("ayanniyi@20")
				       .password("ayanniyi@20")
				       .username("Niyi")
				       .build();
	}
	
	private UserProfileRequest buildUserProfileRequest3() {
		return UserProfileRequest.builder()
				       .bvn("578930")
				       .bankName("Palmpay")
				       .accountNumber("202236285731")
				       .accountName("Alayande Abdulmalik")
				       .password("ayanniyi@20")
				       .city("Sango")
				       .houseNumber("327")
				       .username("blaqmhee")
				       .state("Osun")
				       .userPin("1967")
				       .postCode("204581")
				       .streetName("Crown Estate, Edo Inside, Ajibode, Ijako Ogun State")
				       .build();
	}
}
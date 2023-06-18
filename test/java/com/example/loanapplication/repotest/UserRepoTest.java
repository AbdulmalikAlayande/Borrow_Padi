package com.example.loanapplication.repotest;

import com.example.loanapplication.data.models.BankInfo;
import com.example.loanapplication.data.models.User;
import com.example.loanapplication.data.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepoTest {
	
	@Autowired
	UserRepository userRepository;
	
	User user;
	User savedUser;
	
	
	@BeforeEach
	void startAllTestWith(){
		userRepository.deleteAll();
		user = buildUser();
		savedUser = userRepository.save(user);
	}
	
	@AfterEach
	void endAllTestWith(){
	
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test void saveUser_CountOfUsersIncrease_UserNowHasAnId_UserExistsInTheDatabaseTest(){
		assertEquals(BigInteger.ONE.intValue(), userRepository.count());
		assertNotNull(savedUser);
		assertNotNull(savedUser.getUserId());
		assertNotNull(userRepository.findById(savedUser.getUserId()));
	}
	
	@Test void sameUserCannotBeSavedTwiceTest(){
	
	}
	
	@Test void saveUser_FindSavedUserById_FoundUserIsNotNullTest(){
		Optional<User> foundUser = userRepository.findById(savedUser.getUserId());
		User user = null;
		if (foundUser.isPresent())
			user = foundUser.get();
		assertEquals(user, savedUser);
		assertThat(foundUser).isPresent();
		assertNotNull(foundUser.get().getUserId());
	}
	
	@Test void saveUser_DeleteSavedUserById_FindDeletedUserById_deletedUserDoesExistInTheDatabase(){
		userRepository.deleteById(savedUser.getUserId());
		assertFalse(userRepository.existsById(savedUser.getUserId()));
	}
	
	@Test void getAllUsersExistingInTheDatabaseTest(){
	
	}
	
	@Test void findAllCustomersTest(){
	
	}
	
	@Test void findAllAdminTest(){
	
	}
	private User buildUser(){
		return User.builder()
				       .email("alaabdulmalk03@gmail.com")
				       .password("ayanniyi20")
				       .phoneNumber("12434570")
				       .firstName("abdulmalik")
				       .lastName("alayande")
				       .build();
	}
}

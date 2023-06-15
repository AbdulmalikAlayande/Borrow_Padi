package com.example.loanapplication.repotest;

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
		user = new User();
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
		assertNotNull(savedUser.getId());
		assertNotNull(userRepository.findById(savedUser.getId()));
	}
	
	@Test void sameUserCannotBeSavedTwiceTest(){
		userRepository.save(savedUser);
		assertNotEquals(savedUser, user);
	}
	
	@Test void saveUser_FindSavedUserById_FoundUserIsNotNullTest(){
		Optional<User> foundUser = userRepository.findById(savedUser.getId());
		User user = null;
		if (foundUser.isPresent())
			user = foundUser.get();
		assertEquals(user, savedUser);
		assertThat(foundUser).isPresent();
		assertNotNull(foundUser.get().getId());
	}
	
	@Test void saveUser_DeleteSavedUserById_FindDeletedUserById_deletedUserDoesExistInTheDatabase(){
		userRepository.deleteById(savedUser.getId());
		assertFalse(userRepository.existsById(savedUser.getId()));
	}
	
	@Test void getAllUsersExistingInTheDatabaseTest(){
	
	}
	
	@Test void findAllCustomersTest(){
	
	}
	@Test void findAllAdminTest(){
	
	}
}

package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.exceptions.MessageFailedException;
import com.example.loanapplication.exceptions.RegistrationFailedException;
import com.example.loanapplication.service.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CustomerServiceTest {
	@Autowired
	CustomerService customerService;
	RegistrationRequest registrationRequest;
	RegisterationResponse registerationResponse;
	
	@BeforeEach
	@SneakyThrows
	void startAllTestWith() {
		customerService.deleteAll();
		registrationRequest = buildRegistrationRequest();
		registerationResponse = customerService.registerCustomer(registrationRequest);
	}
	
	@Test void testThatFieldCannotBeEmptyExceptionIsThrownWhenAFieldIsEmpty(){
		RegistrationRequest registrationRequest1 = RegistrationRequest.builder()
				                                           .phoneNumber("556789908976")
				                                           .password("ty@20")
				                                           .lastName("lamidi")
				                                           .firstName("layi")
				                                           .email("dominicrotimi@gmail.com")
				                                           .build();
		//		assertThrows(FieldCannotBeEmptyException.class, () -> customerService.registerCustomer(registrationRequest1));
	}
	
	@Test void registerNewCustomerTest(){
		assertThat(registerationResponse).isNotNull();
	}
	
	@Test void testThatCustomerReceivesAnEmailWhenRegistrationIsSuccessful(){
		
	}
	
	@Test void testThatRegistrationFailedExceptionIsThrownWheneverErrorOccurs(){
	
	}
	
	@Test void testThatCustomerEmailIsValidSavingCustomer(){
		RegistrationRequest registrationRequest1 = RegistrationRequest.builder()
				                                           .email("email#email.com")
				                                           .firstName("Abdulmalik")
				                                           .phoneNumber("07036174617")
				                                           .lastName("Wetter")
				                                           .password("seriki@64")
				                                           .build();
		assertThatThrownBy(() -> {
			customerService.registerCustomer(registrationRequest1);
		})
				.isInstanceOf(RegistrationFailedException.class)
				.hasMessageContaining("Registration Failed");
	}
	
	@Test void loanApplicationTest(){
	
	}
	
	@RepeatedTest(2) void userHasToRegisterBeforeTheyCanApplyForLoanTest(){
		
	}
	
	@AfterEach
	void endAllTestWith() {
		customerService.deleteAll();
	}
	
	private RegistrationRequest buildRegistrationRequest() {
		return RegistrationRequest.builder()
				       .phoneNumber("07036174617")
				       .password("ayanniyi@20")
				       .lastName("Obinali")
				       .firstName("Goodness")
				       .email("obinaligoodness@gmail.com")
				       .build();
	}
}
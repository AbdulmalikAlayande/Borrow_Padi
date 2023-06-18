package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.exceptions.FieldCannotBeEmptyException;
import com.example.loanapplication.exceptions.RegistrationFailedException;
import com.example.loanapplication.service.BorrowPadiCustomerService;
import com.example.loanapplication.service.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceTest {
	@Autowired
	CustomerService customerService;
	RegistrationRequest registrationRequest;
	RegisterationResponse registerationResponse;
	
	@BeforeEach
	@SneakyThrows
	void startAllTestWith() {
		registrationRequest = buildRegistrationRequest();
		registerationResponse = customerService.registerCustomer(registrationRequest);
	}
	
	@Test void testThatNoFieldIsNotEmptyBeforeRegistration() throws RegistrationFailedException {
		RegistrationRequest registrationRequest1 = RegistrationRequest.builder()
				                                           .build();
		assertThrows(FieldCannotBeEmptyException.class, () -> customerService.registerCustomer(registrationRequest1));
	}
	
	@Test void testThatFieldCannotBeEmptyExceptionIsThrownWhenAFieldIsEmpty(){
	
	}
	
	@Test void registerNewCustomerTest(){
		assertThat(registerationResponse).isNotNull();
	}
	
	@Test void testThatCustomerReceivesAnEmailWhenRegistrationIsSuccessful(){
	
	}
	
	@Test void testThatRegistrationFailedExceptionIsThrownWheneverErrorOccurs(){
	
	}
	
	@Test void testThatCustomerEmailIsValidSavingCustomer(){
	
	}
	
	@AfterEach
	void endAllTestWith() {
	}
	
	private RegistrationRequest buildRegistrationRequest() {
		return RegistrationRequest.builder()
				       .phoneNumber("07036174617")
				       .password("ayanniyi20")
				       .lastName("Abdulmalik")
				       .firstName("Alayande")
				       .email("alaabdulmalik03@gmail.com")
				       .build();
	}
}
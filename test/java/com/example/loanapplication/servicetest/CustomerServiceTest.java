package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.exceptions.RegistrationFailedException;
import com.example.loanapplication.service.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

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
	
	@Test void userHasToSetUpTheirProfileBeforeTheyAreEligiBleToApplyForLoanTest(){
		LoanApplicationRequest applicationRequest = LoanApplicationRequest.builder()
				                                            .loanAmount(BigDecimal.valueOf(40_000))
				                                            .userName("Christmas")
				                                            .loanPurpose("For feeding")
				                                            .userPin("1968")
				                                            .loanTenure(30)
				                                            .repaymentPreference("CASH")
				                                            .password("tyson@20")
				                                            .build();
		assertThatThrownBy(()->{
			customerService.applyForLoan(applicationRequest);
		}, "Profile not set up")
				.isInstanceOf(LoanApplicationFailedException.class)
				.hasMessageContaining("Please Set up your profile");
	}
	
	@RepeatedTest(2) void userHasToRegisterBeforeTheyCanApplyForLoanTest(){
		LoanApplicationRequest applicationRequest = LoanApplicationRequest.builder()
				                                            .loanAmount(BigDecimal.valueOf(40_000))
				                                            .userName("Christmas")
				                                            .loanPurpose("For feeding")
				                                            .userPin("1968")
				                                            .loanTenure(30)
				                                            .repaymentPreference("CASH")
				                                            .password("tyson@20")
				                                            .build();
		
		assertThatThrownBy(() -> {
			customerService.applyForLoan(applicationRequest);
		}).isInstanceOf(LoanApplicationFailedException.class)
				.hasMessageContaining("You don't have an account with us, please register to borrow loan");
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
package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;
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
//				assertThrows(FieldCannotBeEmptyException.class, () -> customerService.registerCustomer(registrationRequest1));
	}
	
	@Test void registerNewCustomerTest(){
		assertThat(registerationResponse).isNotNull();
	}
	
	@Test void testThatCustomerReceivesAnEmailWhenRegistrationIsSuccessful(){
		
	}
	
	@Test void testThatRegistrationFailedExceptionIsThrownWheneverErrorLikeMultipleRegistrationOrInvalidCredenetialsOccurs(){
		RegistrationRequest registrationRequest1 = RegistrationRequest.builder()
				                                           .email("email#gmail.com")
				                                           .firstName("Ayanniyi")
				                                           .phoneNumber("07036174617")
				                                           .lastName("Bola")
				                                           .password("seriki@64")
				                                           .username("Habeeb")
				                                           .build();
		assertThatThrownBy(() -> {
			customerService.registerCustomer(registrationRequest1);
		}).isInstanceOf(RegistrationFailedException.class);
	}
	
	@Test void testThatCustomerEmailIsValidSavingCustomer(){
		RegistrationRequest registrationRequest1 = RegistrationRequest.builder()
				                                           .email("email#gmail.com")
				                                           .firstName("Angelo")
				                                           .phoneNumber("+12345981862")
				                                           .lastName("Wetter")
				                                           .password("seriki@64")
				                                           .username("Mike")
				                                           .build();
		assertThatThrownBy(() -> {
			customerService.registerCustomer(registrationRequest1);
		})
				.isInstanceOf(RegistrationFailedException.class)
				.hasMessageContaining("Registration Failed");
	}
	
	@Test void userHasToSetUpTheirProfileBeforeTheyAreEligiBleToApplyForLoanTest() throws ObjectDoesNotExistException{
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
				.isInstanceOf(ObjectDoesNotExistException.class)
				.hasMessageContaining("Please Set up your profile");
	}
	
	@RepeatedTest(2) void userHasToRegisterBeforeTheyCanApplyForLoanTest() throws ObjectDoesNotExistException{
		LoanApplicationRequest applicationRequest = LoanApplicationRequest.builder()
				                                            .loanAmount(BigDecimal.valueOf(40_000))
				                                            .userName("Daniel")
				                                            .loanPurpose("For feeding")
				                                            .userPin("1968")
				                                            .loanTenure(30)
				                                            .repaymentPreference("CASH")
				                                            .password("tyson@20")
				                                            .build();
		
		assertThatThrownBy(() -> {
			customerService.applyForLoan(applicationRequest);
		}).isInstanceOf(ObjectDoesNotExistException.class)
				.hasMessageContaining("Object does not exist\nCaused by incorrect username");
	}
	
	@AfterEach
	void endAllTestWith() {
		customerService.deleteAll();
	}
	
	private RegistrationRequest buildRegistrationRequest() {
		return RegistrationRequest.builder()
				       .phoneNumber("78093462890")
				       .password("sammy#22")
				       .lastName("Sammy")
				       .firstName("cocolate")
				       .username("Samuel Eniola")
				       .email("theeniolasamuel@gmail.com")
				       .build();
	}
}
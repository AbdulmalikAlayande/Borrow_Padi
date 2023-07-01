package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoginRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.FoundUserResponse;
import com.example.loanapplication.data.dtos.responses.LoginResponse;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.exceptions.LoginFailedException;
import com.example.loanapplication.exceptions.MessageFailedException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerServiceTest {
	@Autowired
	CustomerService customerService;
	RegistrationRequest registrationRequest;
	RegisterationResponse registerationResponse;
	private int counter = 1;
	
	@BeforeEach
	@SneakyThrows
	void startAllTestWith() {
	    registrationRequest = buildRegistrationRequest();
		System.out.println("The username is: "+registrationRequest.getUsername());
		customerService.deleteByUsername(registrationRequest.getUsername());
		registerCustomer();
	}
	
	private void registerCustomer() throws RegistrationFailedException, MessageFailedException {
		registerationResponse = customerService.registerCustomer(registrationRequest);
		counter++;
	}
	
//	private RegistrationRequest buildRegistrationRequest2() {
//		return RegistrationRequest.builder()
//				       .phoneNumber("")
//				       .password("Temmy@gold")
//				       .lastName("Temilola")
//				       .firstName("kudirat Alayande")
//				       .username("owolabi")
//				       .email("TemilolaKudirat@gmail.com")
//				       .build();
//	}
	
	
	@Test void registerNewCustomerTest(){
		assertThat(registerationResponse).isNotNull();
	}
	
	@Test void testThatCustomerCannotRegisterMoreThanOnceAndIfTheyDoARegistrationFailedExceptionIsThrown(){
		assertThrows(RegistrationFailedException.class, () -> customerService.registerCustomer(buildRegistrationRequest()),
				"Seems like you already have an account with us");
	}
	
	@Test void testThatRegistrationFailedExceptionIsThrownWheneverErrorLikeMultipleRegistrationOrInvalidCredentialsOccurs(){
		RegistrationRequest registrationRequest1 = RegistrationRequest.builder()
				                                           .email("email#gmail.com")
				                                           .firstName("Ayanniyi")
				                                           .phoneNumber("07036174617")
				                                           .lastName("Bola")
				                                           .password("seriki@64")
				                                           .username("Habeeb")
				                                           .build();
		assertThatThrownBy(() -> customerService.registerCustomer(registrationRequest1)).isInstanceOf(RegistrationFailedException.class);
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
		assertThatThrownBy(() -> customerService.registerCustomer(registrationRequest1))
				.isInstanceOf(RegistrationFailedException.class)
				.hasMessageContaining("Registration Failed");
	}
	
	@Test void testThatUserHasToHaveARegisteredAccountBeforeTheyCanLogin(){
		assertThatThrownBy(()-> customerService.login(buildLoginRequest()))
				.isInstanceOf(LoginFailedException.class).hasMessageContaining("Login Failed");
	}
	
	@Test void testThatLoginFailedExceptionIsThrownWhenUserEntersAnIncorrectPassword(){
		assertThrows(LoginFailedException.class, ()->{
			customerService.login(buildLoginRequest2());
		});
		assertThrows(LoginFailedException.class, ()->{
			customerService.login(buildLoginRequest3());
		});
	}
	
	private LoginRequest buildLoginRequest3() {
		return LoginRequest.builder().email("theeniolasamuel@gmail.com").password("sammy$1").build();
	}
	
	@SneakyThrows
	@Test void testThatCustomerCanLogin_WithEither_TheirEmailAndPassword_Or_UsernameAndPassword(){
		LoginResponse response = customerService.login(LoginRequest.builder().password("sammy#22").email("theeniolasamuel@gmail.com").build());
		assertEquals("Login Successful", response.getMessage());
		LoginResponse response1 = customerService.login(LoginRequest.builder().username("Samuel Eniola").password("sammy#22").build());
		assertEquals("Login Successful", response1.getMessage());
	}
	
	private LoginRequest buildLoginRequest2() {
		return LoginRequest.builder()
				       .username("Samuel Eniola")
				       .password("sammy$1")
				       .build();
	}
	
	private LoginRequest buildLoginRequest() {
		return LoginRequest.builder()
				       .email("oaalayande@gmail.com")
				       .password("oseni@61")
				       .username("ayantunde")
				       .build();
	}
	
	@Test void userHasToSetUpTheirProfileBeforeTheyAreEligibleToApplyForLoanTest() {
		LoanApplicationRequest applicationRequest = LoanApplicationRequest.builder()
				                                            .loanAmount(BigDecimal.valueOf(40_000))
				                                            .userName("Christmas")
				                                            .loanPurpose("For feeding")
				                                            .userPin("1968")
				                                            .loanTenure(30)
				                                            .repaymentPreference("CASH")
				                                            .password("tyson@20")
				                                            .build();
		assertThatThrownBy(()-> customerService.applyForLoan(applicationRequest), "Profile not set up")
				.isInstanceOf(ObjectDoesNotExistException.class)
				.hasMessageContaining("Please Set up your profile");
	}
	
	@RepeatedTest(2) void userHasToRegisterBeforeTheyCanApplyForLoanTest() {
		LoanApplicationRequest applicationRequest = LoanApplicationRequest.builder()
				                                            .loanAmount(BigDecimal.valueOf(40_000))
				                                            .userName("Daniel")
				                                            .loanPurpose("For feeding")
				                                            .userPin("1968")
				                                            .loanTenure(30)
				                                            .repaymentPreference("CASH")
				                                            .password("tyson@20")
				                                            .build();
		
		assertThatThrownBy(() -> customerService.applyForLoan(applicationRequest)).isInstanceOf(ObjectDoesNotExistException.class)
				.hasMessageContaining("Object does not exist\nCaused by incorrect username");
	}
	
	@Test void testThatObjectDoesNotExistExceptionIsThrownWhenTheObjectToBeFoundDoesNotExistInTheDatabase(){
		assertThrows(ObjectDoesNotExistException.class, () -> customerService.findCustomerById("registrationResponse.getId()"));
		assertThrows(ObjectDoesNotExistException.class, () -> customerService.findCustomerByUsernameAndPassword("registrationResponse.getId()", "hello"));
		assertThrows(ObjectDoesNotExistException.class, () -> customerService.findCustomerByUsername("registrationResponse.getId()"));
	}
	
	@SneakyThrows
	@Test void testThatSavedCustomerCanBeFoundByTheCustomerId(){
		Optional<FoundUserResponse> foundCustomer = customerService.findCustomerById(registerationResponse.getId());
		foundCustomer.ifPresent(x -> assertThat(foundCustomer.get().getUserid()).isNotEmpty());
		foundCustomer.ifPresent(x -> assertThat(foundCustomer.get().getMessage()).isEqualTo("User found"));
	}
	
	@SneakyThrows
	@Test void testThatSavedCustomerCanBeRetrievedFromTheDatabaseByTheUsername(){
		Optional<FoundUserResponse> foundCustomer = customerService.findCustomerByUsername("Samuel Eniola");
		foundCustomer.ifPresent(x -> assertThat(foundCustomer.get().getUserid()).isNotEmpty());
		foundCustomer.ifPresent(x -> assertThat(foundCustomer.get().getMessage()).isEqualTo("User Found"));
	}
	
	@SneakyThrows
	@Test void testSavedCustomerCanBeAccessedInTheDatabaseByTheCustomersUsernameAndPassword(){
		Optional<FoundUserResponse> foundCustomer = customerService.findCustomerByUsernameAndPassword("Samuel Eniola", "sammy#22");
		foundCustomer.ifPresent(x -> assertThat(foundCustomer.get().getUserid()).isNotEmpty());
		foundCustomer.ifPresent(x -> assertThat(foundCustomer.get().getMessage()).isEqualTo("User Found"));
	}
	
	@AfterEach void endAllTestWith() {
		customerService.deleteById(registerationResponse.getId());
	}
	
	private RegistrationRequest buildRegistrationRequest() {
		return RegistrationRequest.builder()
				       .phoneNumber("78093462890")
				       .password("#BankyOf@TheWiiBank#")
				       .lastName("Williams")
				       .firstName("Bankole")
				       .username("Banky wii")
				       .email("bankolewilliams@outlook.com")
				       .build();
	}
}
package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.service.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
@SpringBootTest
class LoanApplicationServiceTest {
	
	@Autowired
	LoanApplicationService loanApplicationService;
	@Autowired
	CustomerService customerService;
	
	@BeforeEach
	void setUp() {
	}
	
	@AfterEach
	void tearDown() {
	}
	
	@Test
	@Disabled
	// todo: find a way to set the laon application record to BAD and test it to make sure that loan application fail when loan application record is BAD
	// todo: maybe using the repo at least just to test
	void userHasToHaveAGoodLoanRecordOrBeANewUserBeforeTheyCanApplyForLoan(){
	
	}
	
	@SneakyThrows
	@Test void testThatAWarningIsIssuedIfCustomersLoanApplicationRecordIsNeutral(){
		CustomerService customerService1 = new BorrowPadiCustomerService();
		RegistrationRequest customer = RegistrationRequest.builder().lastName("Omolanke").firstName("Omiyale").email("ObolankeOmiyale@gmail.com")
				                               .password("Obol#yale").username("Omoyale").phoneNumber("09156724351").build();
		customerService1.registerCustomer(customer);
		UserProfileService profileService = new BorrowPadiUserProfileService();
		UserProfileRequest profileRequest = UserProfileRequest.builder().userPin("1798").username("Omoyale").houseNumber("56K").city("Abule-egba")
				                                    .bvn("3456987521").postCode("09876").streetName("Alagemo Onire Street").password("Obol#yale")
				                                    .accountNumber("7897542315").accountName("Obolanke Omiyale").bankName("Palmpay").state("Ogun").build();
		profileService.saveUserProfile(profileRequest);
		LoanApplicationRequest request = LoanApplicationRequest.builder().loanAmount(5000).loanPurpose("for feeding").loanTenure(30).repaymentPreference("Cash")
				                                 .password("Obol#yale").userPin("1798").userName("Oboyale").build();
		LoanApplicationResponse applicationResponse = customerService1.applyForLoan(request);
		assertThat(applicationResponse.getWarning()).isNotNull();
		assertThat(applicationResponse.getWarning())
				.isEqualTo("Maintain A good record and pay the loan on time or else you will be black listed");
	}
	
	@Test void userHasToFillAllNecessaryInformationBeforeTheyAreEligibleToGetLoanAndIfNotLoanApplicationFailedExceptionIsThrown(){
		assertThrowsExactly(LoanApplicationFailedException.class, ()-> customerService.applyForLoan(LoanApplicationRequest.builder()
				                                    .password("ayanniyi@20")
				                                    .userName("blaqmhee")
				                                    .loanTenure(35)
				                                    .userPin("2663")
				                                    .build()), "Loan Application Failed");
	}
	
	@Test void testThatLoanApplicationFailsIfPinIsIncorrect(){
		assertThrowsExactly(LoanApplicationFailedException.class, ()-> customerService.applyForLoan(LoanApplicationRequest.builder()
				                                    .password("ayanniyi@20")
				                                    .loanTenure(30)
				                                    .loanAmount(5000)
				                                    .loanPurpose("for feeding")
				                                    .userPin("3456")
				                                    .userName("blaqmhee")
				                                    .repaymentPreference("CARD")
				                                    .build()), "");
	}
	
	@SneakyThrows
	@Test void testThatLoanApplicationIsSuccessfulIfAllCheckArePassed(){
		LoanApplicationResponse loanResponse = customerService.applyForLoan(buildLoanApplicationRequest());
		assertEquals("Loan Application Successful", loanResponse.getMessage());
	}
	
	private LoanApplicationRequest buildLoanApplicationRequest() {
		return LoanApplicationRequest.builder()
				       .password("ayanniyi@20")
				       .loanTenure(30)
				       .loanAmount(5000)
				       .loanPurpose("for feeding")
				       .userPin("1967")
				       .userName("blaqmhee")
				       .repaymentPreference("CARD")
				       .build();
	}
}

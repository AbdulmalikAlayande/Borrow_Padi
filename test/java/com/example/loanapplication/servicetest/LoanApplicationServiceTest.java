package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.service.CustomerService;
import com.example.loanapplication.service.LoanApplicationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
	void userHasToHaveAGoodLoanRecordOrBeANewUserBeforeTheyCanApplyForLoan(){
		assertThatThrownBy(()-> customerService.applyForLoan(buildLoanApplicationRequest())).isInstanceOf(LoanApplicationFailedException.class)
				.hasMessageContaining("You are not eligible for this loan, you don't have a good record please contact our customer care");
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
				                                    .loanAmount(BigDecimal.valueOf(5000))
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
				       .loanAmount(BigDecimal.valueOf(5000))
				       .loanPurpose("for feeding")
				       .userPin("2339")
				       .userName("blaqmhee")
				       .repaymentPreference("CARD")
				       .build();
	}
}

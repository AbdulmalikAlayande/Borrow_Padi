package com.example.loanapplication.servicetest;

import com.example.loanapplication.service.BorrowPadiLoanApplicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoanApplicationServiceTest {
	
	@Autowired
	BorrowPadiLoanApplicationService loanApplicationService;
	
	@BeforeEach
	void setUp() {
	}
	
	@AfterEach
	void tearDown() {
	}
	
	@Test
	void userHasToHaveAGoodLoanRecordOrBeANewUserBeforeTheyCanApplyForLoan(){
	
	}
	
	@Test void userHasToFillAllNecessaryInformationsBeforeTheyAreEligibleToGetLoanAndIfNotLoanApplicationFailedExceptionIsThrown(){
	
	}
	
	
}
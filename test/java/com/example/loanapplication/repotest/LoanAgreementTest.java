package com.example.loanapplication.repotest;

import com.example.loanapplication.data.models.LoanAgreementForm;
import com.example.loanapplication.data.repositories.LoanAgreementRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class LoanAgreementTest {
	
	LoanAgreementForm agreementForm;
	LoanAgreementForm savedForm;
	@Autowired
	LoanAgreementRepo agreementRepo;
	
	@BeforeEach void startAllTestWith(){
		agreementRepo.deleteAll();
		agreementForm = new LoanAgreementForm();
		savedForm = agreementRepo.save(agreementForm);
	}
	
	@AfterEach void endAllTestWith(){
	
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test void saveLoanAgreementForm_LoanAgreementFormNowHasAnIdAfterSavingTest(){
		assertNotNull(savedForm);
		assertNotNull(savedForm.getId());
	}
	
	@Test void saveLoanAgreementForm_FormExistsInTheDatabaseTest(){
		assertTrue(agreementRepo.existsById(savedForm.getId()));
		assertEquals(savedForm, agreementForm);
	}
	
	@Test void saveLoanAgreementForm_FindSavedLoanAgreementFormById_FoundLoanAgreementFormIsNotNullTest(){
		assertNotNull(agreementRepo.findById(savedForm.getId()));
		assertEquals(savedForm, agreementForm);
	}
	
	@Test void saveLoanAgreementForm_FindSavedLoanAgreementFormById_FoundLoanAgreementFormActuallyReturnsAnObjectWithIdTest(){
		Optional<LoanAgreementForm> foundForm = agreementRepo.findById(savedForm.getId());
		foundForm.ifPresent(loanAgreementForm -> assertNotNull(loanAgreementForm.getId()));
		foundForm.ifPresent(loanAgreementForm -> assertSame(loanAgreementForm.getId(), savedForm.getId()));
	}
	
	@Test void saveLoanAgreementForm_DeleteSavedLoanAgreementFormById_FindDeletedFormById_FormDoesNotExistsTest(){
		agreementRepo.deleteById(savedForm.getId());
		Optional<LoanAgreementForm> foundForm = agreementRepo.findById(savedForm.getId());
		foundForm.ifPresent(loanAgreementForm -> assertNull(loanAgreementForm.getId()));
		assertFalse(agreementRepo.existsById(savedForm.getId()));
	}
	
	@Test void getAllLoanAgreementFormsInTheDatabaseTest(){
		List<LoanAgreementForm> allForms = agreementRepo.findAll();
		for (LoanAgreementForm form : allForms) {
			if (form != null) log.info("Form {} is null", form);
			assertNotNull(form);
		}
	}
	
	@Test void getAllLoanAgreementFormsWithTheSameDate_AllLoanAgreementFormsWithTheSameDateInTheDatabaseAreReturnedTest(){
	
	}
	
	@Test void saveLoanAgreementForm_FindSavedLoanAgreementFormByApplicationDateAndApplicationTime_FoundLoanAgreementFormIsNotNullTest(){
	
	}
}

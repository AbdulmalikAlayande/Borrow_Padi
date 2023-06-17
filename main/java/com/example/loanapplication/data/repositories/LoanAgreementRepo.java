package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.LoanAgreementForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface LoanAgreementRepo extends JpaRepository<LoanAgreementForm, String> {
	
	Optional<LoanAgreementForm> findLoanAgreementFormByApplicationDateAndApplicationTime(LocalDate applicationDate, LocalTime applicationTime);
	
	Optional<List<LoanAgreementForm>> findAllByApplicationDate(LocalDate applicationDate);
}

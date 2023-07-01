package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.LoanApplicationForm;
import com.example.loanapplication.data.models.LoanStatus;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepo extends JpaRepository<LoanApplicationForm, String> {
	
	Optional<List<LoanApplicationForm>> findAllByApplicationDate(LocalDate date);
	Optional<LoanApplicationForm> findByApplicationDateAndApplicationTime(LocalDate date, LocalTime time);
	Optional<List<LoanApplicationForm>> findAllByStatus(LoanStatus status);
	Optional<List<LoanApplicationForm>> findAllByLoanAmountGreaterThan(@NonNull BigDecimal loanAmount);
	Optional<List<LoanApplicationForm>> findAllByLoanAmountLessThan(@NonNull BigDecimal loanAmount);
}

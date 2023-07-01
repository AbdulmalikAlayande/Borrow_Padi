package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoanApplicationService {
	
	LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest);
	Optional<LoanApplicationResponse> findLoanById(String loanId);
	Optional<List<LoanApplicationResponse>> findAllLoanByLoanStatus();
	Optional<List<LoanApplicationResponse>> findAllByLoanAmountGreaterThan(@NonNull BigDecimal loanAmount);
	Optional<List<LoanApplicationResponse>> findAllByLoanAmountLessThan(@NonNull BigDecimal loanAmount);
}

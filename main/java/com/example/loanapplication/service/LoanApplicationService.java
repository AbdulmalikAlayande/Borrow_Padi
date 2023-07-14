package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoanStatusViewRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.dtos.responses.LoanStatusViewResponse;
import com.example.loanapplication.data.models.LoanStatus;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.exceptions.NoSuchLoanException;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoanApplicationService {
	
	LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException;
	Optional<LoanApplicationResponse> findLoanById(Long loanId) throws NoSuchLoanException;
	Optional<List<LoanApplicationResponse>> findAllLoanByLoanStatus(LoanStatus loanStatus);
	Optional<List<LoanApplicationResponse>> findAllByLoanAmountGreaterThan(@NonNull BigDecimal loanAmount);
	Optional<List<LoanApplicationResponse>> findAllByLoanAmountLessThan(@NonNull BigDecimal loanAmount);
	LoanStatusViewResponse viewLoanStatus(LoanStatusViewRequest loanStatusViewRequest) throws NoSuchLoanException;
	Optional<List<LoanStatusViewResponse>> getAllLoans(String username, String password);
}

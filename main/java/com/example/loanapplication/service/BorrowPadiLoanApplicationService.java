package com.example.loanapplication.service;

import com.example.loanapplication.LoanApplication;
import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.models.LoanApplicationForm;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.utils.Mapper;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowPadiLoanApplicationService implements LoanApplicationService{
	@Override
	public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) {
		try {
			LoanApplicationForm mappedForm = Mapper.map(loanApplicationRequest);
		}catch (Throwable exception){
			LoanApplicationFailedException failedException = new LoanApplicationFailedException(exception.getMessage());
			failedException.setCause(exception.getCause());
			throw failedException;
		}
		return null;
	}
	
	@Override
	public Optional<LoanApplicationResponse> findLoanById(String loanId) {
		return Optional.empty();
	}
	
	@Override
	public Optional<List<LoanApplicationResponse>> findAllLoanByLoanStatus() {
		return Optional.empty();
	}
	
	@Override
	public Optional<List<LoanApplicationResponse>> findAllByLoanAmountGreaterThan(@NonNull BigDecimal loanAmount) {
		return Optional.empty();
	}
	
	@Override
	public Optional<List<LoanApplicationResponse>> findAllByLoanAmountLessThan(@NonNull BigDecimal loanAmount) {
		return Optional.empty();
	}
}

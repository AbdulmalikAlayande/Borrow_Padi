package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoanStatusViewRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.dtos.responses.LoanStatusViewResponse;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;
import com.example.loanapplication.data.models.LoanApplicationForm;
import com.example.loanapplication.data.models.LoanPaymentRecord;
import com.example.loanapplication.data.repositories.LoanApplicationRepo;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.exceptions.NoSuchLoanException;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;
import com.example.loanapplication.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BorrowPadiLoanApplicationService implements LoanApplicationService{
	@Autowired
	LoanApplicationRepo applicationRepo;
	@Autowired
	UserProfileService profileService;
	@Override
	public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException{
		try {
			LoanApplicationForm mappedForm = Mapper.map(loanApplicationRequest);
			applicationRepo.save(mappedForm);
			if(userLoanRepaymentRecordIsNeutral(loanApplicationRequest))
				return applicationResponseWithWarning();
			return LoanApplicationResponse.builder().message("Loan Application Successful").build();
		}catch (Throwable exception){
			LoanApplicationFailedException failedException = new LoanApplicationFailedException(exception.getMessage());
			failedException.setCause(exception.getCause());
			throw failedException;
		}
	}
	
	private LoanApplicationResponse applicationResponseWithWarning() {
		return LoanApplicationResponse.builder()
				       .warning("Maintain A good record and pay the loan on time or else you will be black listed")
				       .message("Loan Application Successful").build();
	}
	
	//
	private boolean userLoanRepaymentRecordIsNeutral(LoanApplicationRequest loanApplicationRequest) throws ObjectDoesNotExistException {
		Optional<UserProfileResponse> profileResponse = profileService.findUserProfileByUsername(loanApplicationRequest.getUserName());
		if (profileResponse.isPresent()){
			return profileResponse.get().getRecord() == LoanPaymentRecord.NEUTRAL;
		};
		return false;
	}
	
	@Override
	public Optional<LoanApplicationResponse> findLoanById(String loanId) throws NoSuchLoanException{
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
	
	@Override
	public LoanStatusViewResponse viewLoanStatus(LoanStatusViewRequest loanStatusViewRequest) throws NoSuchLoanException {
		return null;
	}
	
	@Override
	public Optional<List<LoanStatusViewResponse>> getAllLoans(String username, String password) {
		return Optional.empty();
	}
}

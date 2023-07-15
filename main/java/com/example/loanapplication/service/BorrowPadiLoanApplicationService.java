package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoanStatusViewRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.dtos.responses.LoanStatusViewResponse;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;
import com.example.loanapplication.data.dtos.updaterequests.UpdateRequest;
import com.example.loanapplication.data.models.LoanApplicationForm;
import com.example.loanapplication.data.models.LoanPaymentRecord;
import com.example.loanapplication.data.models.LoanStatus;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BorrowPadiLoanApplicationService implements LoanApplicationService{
	private static final String FILE_NAME = "BorrowPadiLoanApplicationService.java";
	@Autowired
	LoanApplicationRepo applicationRepo;
	@Autowired
	UserProfileService profileService;
	
	
	@Override
	public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException{
		CustomerService customerService = new BorrowPadiCustomerService();
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.setUsername(loanApplicationRequest.getUsername());
		try {
			LoanApplicationForm mappedForm = Mapper.map(loanApplicationRequest);
			LoanApplicationForm savedForm = applicationRepo.save(mappedForm);
			Objects.requireNonNull(updateRequest.getApplicationFormSet(), "method getApplicationForm() is null").add(savedForm);
			customerService.updateDetails(updateRequest);
			if(userLoanRepaymentRecordIsNeutral(loanApplicationRequest))
				return applicationResponseWithWarning(savedForm.getApplicationFormId());
			return LoanApplicationResponse.builder().message("Loan Application Successful").ApplicationFormId(savedForm.getApplicationFormId()).build();
		}catch (Throwable exception){
			LoanApplicationFailedException failedException = new LoanApplicationFailedException(exception.getMessage());
			failedException.setCause(exception.getCause());
			throw failedException;
		}
	}
	
	private LoanApplicationResponse applicationResponseWithWarning(Long applicationFormId) {
		return LoanApplicationResponse.builder()
				       .warning("Maintain A good record and pay the loan on time or else you will be black listed")
				       .ApplicationFormId(applicationFormId)
				       .message("Loan Application Successful").build();
	}
	
	private boolean userLoanRepaymentRecordIsNeutral(LoanApplicationRequest loanApplicationRequest) throws ObjectDoesNotExistException {
		Optional<UserProfileResponse> profileResponse = profileService.findUserProfileByUsername(loanApplicationRequest.getUsername());
		return profileResponse.filter(userProfileResponse -> userProfileResponse.getRecord() == LoanPaymentRecord.NEUTRAL).isPresent();
	}
	
	@Override
	public Optional<LoanApplicationResponse> findLoanById(Long loanId) throws NoSuchLoanException{
		LoanApplicationResponse loanApplicationResponse = new LoanApplicationResponse();
		Optional<LoanApplicationForm> foundApplicationForm = applicationRepo.findById(loanId);
		foundApplicationForm
				.ifPresentOrElse(loanApplicationForm ->
					Mapper.map(loanApplicationForm, loanApplicationResponse),
				()-> throwNoSuchLoanException(String.valueOf(loanId), "findLoanById", 68));
		return Optional.of(loanApplicationResponse);
	}
	
	@Override
	public Optional<List<LoanApplicationResponse>> findAllLoanByLoanStatus(LoanStatus loanStatus) {
		List<LoanApplicationResponse> loanApplicationResponseList = new ArrayList<>();
		Optional<List<LoanApplicationForm>> foundApplicationForms = applicationRepo.findAllByStatus(loanStatus);
		foundApplicationForms
			.ifPresentOrElse(loanApplicationForms ->
				loanApplicationForms
					.stream()
					.findAny()
					.ifPresent(elements -> Mapper.map(loanApplicationForms, loanApplicationResponseList)),
				() -> throwNoSuchLoanException(String.valueOf(loanStatus), "findAllLoanByLoanStatus", 80));
		return Optional.of(loanApplicationResponseList);
	}
	
	@Override
	public Optional<List<LoanApplicationResponse>> findAllByLoanAmountGreaterThan(@NonNull BigDecimal loanAmount) {
		Optional<List<LoanApplicationForm>> foundLoans = applicationRepo.findAllByLoanAmountGreaterThan(loanAmount);
		String message = "There is no loan greater than the amount ";
		String methodName ="findAllByLoanAmountGreaterThan";
		return loanApplicationResponses(foundLoans, loanAmount, message, methodName);
	}
	@Override
	public Optional<List<LoanApplicationResponse>> findAllByLoanAmountLessThan(@NonNull BigDecimal loanAmount) {
		Optional<List<LoanApplicationForm>> foundLoans = applicationRepo.findAllByLoanAmountLessThan(loanAmount);
		String message = "There is no loan lesser than the amount ";
		String methodName = "findAllByLoanAmountLessThan";
		return loanApplicationResponses(foundLoans, loanAmount, message, methodName);
	}
	
	private Optional<List<LoanApplicationResponse>> loanApplicationResponses(Optional<List<LoanApplicationForm>> foundLoans, BigDecimal loanAmount, String message, String methodName) {
		List<LoanApplicationResponse> loanApplicationResponseList = new ArrayList<>();
		foundLoans.ifPresentOrElse(loanApplicationForms->
				  loanApplicationForms
				        .stream()
				        .findAny()
				        .ifPresent(form-> Mapper.map(loanApplicationForms, loanApplicationResponseList)),
				  ()-> throwNoSuchLoanException(message+loanAmount, methodName, 106));
		return Optional.of(loanApplicationResponseList);
	}
	
	private void throwNoSuchLoanException(String loanIdentity, String methodName, int lineNumber) {
		NoSuchLoanException exception = new NoSuchLoanException("No loan found, There is no loan with status "+loanIdentity);
		StackTraceElement[] stackTraceElements = new StackTraceElement[]{
				new StackTraceElement(String.valueOf(this.getClass()), methodName, FILE_NAME, lineNumber),
				new StackTraceElement(String.valueOf(this.getClass()), "throwNoSuchLoanException", FILE_NAME,95)
		};
		exception.setStackTrace(stackTraceElements);
		throw exception;
	}
	
	@Override
	public Optional<List<LoanStatusViewResponse>> getAllLoans(String username, String password) {
		return Optional.empty();
	}
	
	@Override
	public LoanStatusViewResponse viewLoanStatus(LoanStatusViewRequest loanStatusViewRequest) throws NoSuchLoanException {
		return null;
	}
}

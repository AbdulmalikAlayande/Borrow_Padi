package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoanStatusViewRequest;
import com.example.loanapplication.data.dtos.requests.LoginRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.*;
import com.example.loanapplication.data.dtos.updaterequests.UpdateRequest;
import com.example.loanapplication.data.dtos.updateresponse.UpdateResponse;
import com.example.loanapplication.exceptions.*;

import java.util.Optional;

public interface CustomerService {
	RegisterationResponse registerCustomer(RegistrationRequest registerationRequest) throws RegistrationFailedException, FieldCannotBeEmptyException, MessageFailedException;
	LoginResponse login(LoginRequest loginRequest) throws LoginFailedException;
	LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException, ObjectDoesNotExistException;
	void agreeToTermsAndConditionForLoanApplication();
	UpdateResponse updateDetails(UpdateRequest updateRequest);
	LoanStatusViewResponse viewLoanStatus(LoanStatusViewRequest loanStatusViewRequest) throws NoSuchLoanException;
	Optional<FoundUserResponse> findCustomerById(String customerId) throws ObjectDoesNotExistException;
	Optional<FoundUserResponse> findCustomerByUsername(String username) throws ObjectDoesNotExistException;
	Optional<FoundUserResponse> findCustomerByUsernameAndPassword(String username, String password) throws ObjectDoesNotExistException;
	
	void deleteAll();
	
	void deleteById(String id);
	
	void deleteByUsername(String username);
//	Optional<FoundUserResponse>
}

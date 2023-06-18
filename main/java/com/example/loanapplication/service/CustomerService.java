package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.*;
import com.example.loanapplication.data.dtos.responses.*;
import com.example.loanapplication.data.dtos.updaterequests.UpdateRequest;
import com.example.loanapplication.data.dtos.updateresponse.UpdateResponse;
import com.example.loanapplication.exceptions.*;

public interface CustomerService {
	RegisterationResponse registerCustomer(RegistrationRequest registerationRequest) throws RegistrationFailedException, FieldCannotBeEmptyException;
	LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException;
	void agreeToTermsAndConditionForLoanApplication();
	UpdateResponse updateDetails(UpdateRequest updateRequest);
	LoanStatusViewResponse viewLoanStatus(LoanStatusViewRequest loanStatusViewRequest) throws NoSuchLoanException;
}

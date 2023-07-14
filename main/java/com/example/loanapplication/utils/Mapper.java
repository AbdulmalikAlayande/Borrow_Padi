package com.example.loanapplication.utils;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.data.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Mapper {
	
	public static RegisterationResponse map(Customer customer){
		String firstName = customer.getUser().getFirstName();
		String lastName = customer.getUser().getLastName();
		return RegisterationResponse.builder()
				       .message("Congratulations "+firstName+" "+lastName+" Your registration was successful")
				       .id(customer.getCustomerId())
				       .build();
	}
	
	public static User map(RegistrationRequest registrationRequest){
		return User.builder()
				       .username(registrationRequest.getUsername())
				       .password(registrationRequest.getPassword())
				       .token(AppUtils.generatedToken())
				       .phoneNumber(registrationRequest.getPhoneNumber())
				       .email(registrationRequest.getEmail())
				       .firstName(registrationRequest.getFirstName())
				       .lastName(registrationRequest.getLastName())
				       .build();
	}
	
	public static void map(Customer customer, User user){
		customer.setUser(user);
		customer.setLoggedIn(true);
		customer.setLastTimeLoggedIn(LocalDateTime.now());
	}
	
	public static LoanApplicationForm map(LoanApplicationRequest loanApplicationRequest) {
		long loanTenure = loanApplicationRequest.getLoanTenure();
		LocalDate currentDate = LocalDate.now();
		LocalDate repaymentDate = currentDate.plusDays(loanTenure);
		return LoanApplicationForm.builder()
				        .applicationDate(LocalDate.now())
				        .applicationTime(LocalTime.now())
				        .loanPurpose(loanApplicationRequest.getLoanPurpose())
				        .loanAmount(BigDecimal.valueOf(loanApplicationRequest.getLoanAmount()))
				        .status(LoanStatus.PENDING)
				        .repaymentPreference(PaymentMethod.valueOf(loanApplicationRequest.getRepaymentPreference().toUpperCase()))
				        .repaymentDate(repaymentDate)
				        .requestedTermsAndCondition("You have to payback on the due date")
				        .build();
	}
	
	public static BankInfo map(UserProfileRequest userProfileRequest) {
		return BankInfo.builder()
				       .name(userProfileRequest.getAccountName())
				       .bvn(userProfileRequest.getBvn())
				       .bankName(userProfileRequest.getBankName())
				       .accountNumber(userProfileRequest.getAccountNumber())
				       .build();
	}
	
	public static LoanApplicationResponse map(LoanApplicationForm loanApplicationForm) {
		return LoanApplicationResponse.builder()
				       .ApplicationFormId(loanApplicationForm.getApplicationFormId())
				       .message("Form Found")
				       .build();
	}
	public static void map(LoanApplicationForm loanApplicationForm, LoanApplicationResponse loanApplicationResponse) {
		loanApplicationResponse.setApplicationFormId(loanApplicationForm.getApplicationFormId());
		loanApplicationResponse.setMessage("Application Successful");
	}
}

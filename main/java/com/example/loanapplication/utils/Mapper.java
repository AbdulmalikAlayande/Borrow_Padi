package com.example.loanapplication.utils;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.data.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
				        .repaymentPreference(PaymentMethod.valueOf(loanApplicationRequest.getRepaymentPreference()))
				        .repaymentDate(repaymentDate)
				        .requestedTermsAndCondition("")
				        .build();
	}
}

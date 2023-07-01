package com.example.loanapplication.utils;

import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.data.models.Customer;
import com.example.loanapplication.data.models.User;

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
}

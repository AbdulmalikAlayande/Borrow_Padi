package com.example.loanapplication.utils;

import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.data.models.Customer;

public class Mapper {
	
	public static RegisterationResponse map(Customer customer){
		String firstName = customer.getUser().getFirstName();
		String lastName = customer.getUser().getLastName();
		return RegisterationResponse.builder()
				       .message("Congratulations "+firstName+" "+lastName+"Your registration was successful")
				       .build();
	}
}

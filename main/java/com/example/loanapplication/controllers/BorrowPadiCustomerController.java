package com.example.loanapplication.controllers;

import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.exceptions.MessageFailedException;
import com.example.loanapplication.exceptions.RegistrationFailedException;
import com.example.loanapplication.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("my_api/v8")
@AllArgsConstructor
@RequiredArgsConstructor
public class BorrowPadiCustomerController {
	
	@Autowired
	private CustomerService customerService;

	@PostMapping("/register-new-user/")
	public RegisterationResponse registerNewCustomer(@RequestBody RegistrationRequest registrationRequest){
		try {
			return customerService.registerCustomer(registrationRequest);
		} catch (RegistrationFailedException | MessageFailedException e) {
			return RegisterationResponse.builder()
					       .message("Registration Failed")
					       .build();
		}
	}
	
}

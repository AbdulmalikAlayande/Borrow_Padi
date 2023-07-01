package com.example.loanapplication.controllers;

import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.exceptions.MessageFailedException;
import com.example.loanapplication.exceptions.RegistrationFailedException;
import com.example.loanapplication.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("my_api/v8")
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class BorrowPadiCustomerController {
	
	@Autowired
	private CustomerService customerService;

	@PostMapping("/register-new-user/")
	public RegisterationResponse registerNewCustomer(@RequestBody RegistrationRequest registrationRequest){
		try {
			return customerService.registerCustomer(registrationRequest);
		} catch (RegistrationFailedException | MessageFailedException e) {
			log.info("Exception caught at controller level {}", e.getMessage());
			return RegisterationResponse.builder()
					       .message("Registration Failed")
					       .build();
		}
	}
	
	public RegisterationResponse findCustomerByUsername(String username){
		return null;
	}
	
}

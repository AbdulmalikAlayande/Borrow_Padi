package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.EmailRequest;
import com.example.loanapplication.exceptions.MessageFailedException;

public interface MailService {
	
	void isValidEmail(String email);
	void isValidPassword(String password);
	String emailCustomerToVerifyTheirCustomer(EmailRequest emailRequest) throws MessageFailedException;
}

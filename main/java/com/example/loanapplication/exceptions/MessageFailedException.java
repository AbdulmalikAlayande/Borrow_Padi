package com.example.loanapplication.exceptions;

import org.springframework.messaging.converter.StringMessageConverter;

import javax.mail.MessagingException;

public class MessageFailedException extends MessagingException {
	
	public MessageFailedException(String message){
		super(message);
	}
}

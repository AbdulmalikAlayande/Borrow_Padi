package com.example.loanapplication.exceptions;

import javax.security.auth.login.FailedLoginException;

public class RegistrationFailedException extends FailedLoginException {
	public RegistrationFailedException(String message){
		super(message);
	}
}

package com.example.loanapplication.exceptions;

import javax.security.auth.login.FailedLoginException;

public class LoginFailedException extends FailedLoginException {
	
	public LoginFailedException(String message){
		super(message);
	}
}

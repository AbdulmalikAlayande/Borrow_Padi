package com.example.loanapplication.exceptions;

public class LoanApplicationFailedException extends RuntimeException{
	public LoanApplicationFailedException(String message){
		super(message);
	}
}

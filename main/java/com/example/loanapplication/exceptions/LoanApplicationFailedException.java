package com.example.loanapplication.exceptions;

public class LoanApplicationFailedException extends RuntimeException{
	private String cause;
	public LoanApplicationFailedException(String message){
		super(message);
	}
	
	public void setCause(String cause){
		this.cause = cause;
	}
	
	public String getExceptionCause() {
		return cause;
	}
	
}

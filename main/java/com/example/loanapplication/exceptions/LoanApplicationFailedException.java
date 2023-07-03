package com.example.loanapplication.exceptions;

public class LoanApplicationFailedException extends RuntimeException{
	private String cause;
	private Throwable exceptionCause;
	public LoanApplicationFailedException(String message){
		super(message);
	}
	
	public void setCause(String cause){
		this.cause = cause;
	}
	
	public void setCause(Throwable cause){
		exceptionCause = cause;
	}
	
	public String getExceptionCause() {
		return cause;
	}
	public Throwable getCause(){
		if (exceptionCause != null && exceptionCause == this)
			return exceptionCause;
		return super.getCause();
	}
	
}

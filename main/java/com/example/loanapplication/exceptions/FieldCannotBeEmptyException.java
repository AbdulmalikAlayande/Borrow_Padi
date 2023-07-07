package com.example.loanapplication.exceptions;

public class FieldCannotBeEmptyException extends IllegalArgumentException{
	
	private Throwable cause;
	
	@Override
	public Throwable getCause() {
		return cause;
	}
	
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	public FieldCannotBeEmptyException(String message){
		super(message);
	}
}

package com.example.loanapplication.exceptions;

import java.util.NoSuchElementException;

public class NoSuchLoanException extends NoSuchElementException {
	public NoSuchLoanException(String message){
		super(message);
	}
	
	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		super.setStackTrace(stackTrace);
	}
	
	public StackTraceElement[] getStackTrace(){
		return super.getStackTrace();
	}
}

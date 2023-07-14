package com.example.loanapplication.exceptions;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class NoSuchLoanException extends NoSuchElementException {
	private StackTraceElement[] stackTraceElements;
	private String exceptionCause;
	public NoSuchLoanException(String message){
		super(message);
	}
	
	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		if (Arrays.stream(stackTrace).findAny().isPresent())
			stackTraceElements = stackTrace;
	}
	
	public StackTraceElement[] getStackTrace(){
		if (Arrays.stream(stackTraceElements).findAny().isPresent())
			return stackTraceElements;
		return super.getStackTrace();
	}
	
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void setCause(String cause){
		this.exceptionCause = cause;
	}
	
	public String getExceptionCause(){
		return exceptionCause;
	}
}

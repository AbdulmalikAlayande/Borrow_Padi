package com.example.loanapplication.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.internal.build.AllowPrintStacktrace;

@AllowPrintStacktrace
public class ObjectDoesNotExistException extends Exception {
	
	private Throwable cause;
	private String exceptionCause;
	
	public ObjectDoesNotExistException(String message) {
		super(message);
	}
	
	@Override
	public Throwable getCause() {
		if (super.getCause() != null)
			return super.getCause();
		return cause;
	}
	
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	public String getExceptionCause() {
		return exceptionCause;
	}
	
	public void setExceptionCause(String exceptionCause) {
		this.exceptionCause = exceptionCause;
	}
}

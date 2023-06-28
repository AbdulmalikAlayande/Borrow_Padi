package com.example.loanapplication.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.internal.build.AllowPrintStacktrace;

@AllowPrintStacktrace
public class ObjectDoesNotExistException extends Exception {
	public ObjectDoesNotExistException(String message) {
		super(message);
	}
}

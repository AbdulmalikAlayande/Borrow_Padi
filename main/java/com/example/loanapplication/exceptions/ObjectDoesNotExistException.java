package com.example.loanapplication.exceptions;

import org.hibernate.ObjectNotFoundException;

public class ObjectDoesNotExistException extends Exception {
	public ObjectDoesNotExistException(String message) {
		super(message);
	}
}

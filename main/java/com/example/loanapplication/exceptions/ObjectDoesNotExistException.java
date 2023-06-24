package com.example.loanapplication.exceptions;

import org.hibernate.ObjectNotFoundException;

public class ObjectDoesNotExistException extends IllegalArgumentException {
	public ObjectDoesNotExistException(String message) {
		super(message);
	}
}

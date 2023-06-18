package com.example.loanapplication.exceptions;

import java.util.NoSuchElementException;

public class NoSuchLoanException extends NoSuchElementException {
	public NoSuchLoanException(String message){
		super(message);
	}
}

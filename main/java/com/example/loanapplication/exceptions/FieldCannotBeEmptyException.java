package com.example.loanapplication.exceptions;

public class FieldCannotBeEmptyException extends IllegalArgumentException{
	
	public FieldCannotBeEmptyException(String message){
		super(message);
	}
}

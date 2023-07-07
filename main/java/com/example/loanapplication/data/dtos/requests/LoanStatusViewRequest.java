package com.example.loanapplication.data.dtos.requests;

import java.time.LocalDate;
import java.time.LocalTime;

public class LoanStatusViewRequest {
	
	private LocalDate loanApplicationDate;
	private LocalTime loanApplicationTime;
	private String userPassword;
	private String username;
}

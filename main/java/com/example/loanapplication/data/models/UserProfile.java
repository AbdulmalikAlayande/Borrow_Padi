package com.example.loanapplication.data.models;

import java.math.BigDecimal;

public class UserProfile {
	private String userId;
	private BigDecimal loanLimit;
	private int loanLevel;
	private LoanPaymentRecord record;
	private Address address;
}

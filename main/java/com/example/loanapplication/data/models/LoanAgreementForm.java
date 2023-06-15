package com.example.loanapplication.data.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanAgreementForm {
	private String requestedTermsAndCondition;
	private LocalDate applicationDate;
	private LocalTime applicationTime;
	private LocalDate repaymentDate;
	private BigDecimal loanInterest;
	private BigDecimal repaymentAmount;
	private PaymentMethod repaymentPreference;
}

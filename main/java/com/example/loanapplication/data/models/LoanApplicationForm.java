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
public class LoanApplicationForm {
	private String userId;
	private String requestedTermsAndCondition;
	private BigDecimal loanAmount;
	private String loanPurpose;
	private PaymentMethod repaymentPreference;
	private LoanStatus status;
	private BankInfo info;
	private String loanRejectionReason;
	private LocalDate applicationDate;
	private LocalTime applicationTime;
	private LocalDate repaymentDate;
}

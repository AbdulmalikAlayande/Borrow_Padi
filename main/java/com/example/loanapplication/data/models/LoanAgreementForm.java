package com.example.loanapplication.data.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanAgreementForm {
	@NonNull
	private String requestedTermsAndCondition;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@NonNull
	private LocalDate applicationDate;
	@NonNull
	private LocalTime applicationTime;
	@NonNull
	private LocalDate repaymentDate;
	@NonNull
	private BigDecimal loanInterest;
	@NonNull
	private BigDecimal repaymentAmount;
	@NonNull
	private PaymentMethod repaymentPreference;
}

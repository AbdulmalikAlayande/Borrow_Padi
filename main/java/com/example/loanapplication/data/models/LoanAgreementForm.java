package com.example.loanapplication.data.models;


import jakarta.persistence.*;
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
	@GeneratedValue(strategy = GenerationType.UUID)
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
	@Enumerated(EnumType.STRING)
	private PaymentMethod repaymentPreference;
}

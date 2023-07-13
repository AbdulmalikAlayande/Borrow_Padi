package com.example.loanapplication.data.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationForm {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long applicationFormId;
	private String requestedTermsAndCondition;
	@NonNull
	private BigDecimal loanAmount;
	@NonNull
	private String loanPurpose;
	@NonNull
	@Enumerated(EnumType.STRING)
	private PaymentMethod repaymentPreference;
	@NonNull
	@Enumerated(EnumType.STRING)
	private LoanStatus status;
	@Nullable
	private String loanRejectionReason;
	@NonNull
	private LocalDate applicationDate;
	@NonNull
	private LocalTime applicationTime;
	@NonNull
	private LocalDate repaymentDate;
}

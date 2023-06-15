package com.example.loanapplication.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.ManyToAny;

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
	@NonNull
	private String userId;
	@NonNull
	private String requestedTermsAndCondition;
	@NonNull
	private BigDecimal loanAmount;
	@NonNull
	private String loanPurpose;
	@NonNull
	private PaymentMethod repaymentPreference;
	@NonNull
	private LoanStatus status;
	@NonNull
	@Embedded
	private BankInfo info;
	@NonNull
	private String loanRejectionReason;
	@NonNull
	private LocalDate applicationDate;
	@NonNull
	private LocalTime applicationTime;
	@NonNull
	private LocalDate repaymentDate;
}

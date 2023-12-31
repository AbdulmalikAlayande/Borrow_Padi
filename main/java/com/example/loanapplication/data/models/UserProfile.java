package com.example.loanapplication.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String profileId;
	@NonNull
	@OneToOne
	private User user;
	private BigDecimal loanLimit;
	private int loanLevel;
	@Enumerated(EnumType.STRING)
	private LoanPaymentRecord loanRepaymentRecord;
	@OneToOne
	private Address address;
	@OneToOne
	private BankInfo info;
	private String userPin;
	@Column(unique = true)
	private String username;
	private boolean hasPendingLoan;
}

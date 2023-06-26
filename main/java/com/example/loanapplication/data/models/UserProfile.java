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
	private LoanPaymentRecord record;
	@OneToOne
	private Address address;
	@org.springframework.lang.NonNull
	@OneToOne(cascade = CascadeType.ALL)
	private BankInfo info;
	private String userPin;
	@Column(unique = true)
	private String username;
}

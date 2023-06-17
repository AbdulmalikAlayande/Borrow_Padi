package com.example.loanapplication.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Customer{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String customerId;
	@NonNull
	@OneToOne
	private User user;
	@NonNull
	@OneToMany
	private List<LoanApplicationForm> applicationFormSet;
	@OneToMany
	@NonNull
	private List<LoanAgreementForm> agreementForms;
}
                                                                                                        
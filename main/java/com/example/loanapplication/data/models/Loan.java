package com.example.loanapplication.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long loanId;
	@OneToOne
	private User user;
	@OneToOne
	private LoanAgreementForm  agreementForm;
	@OneToOne
	private LoanApplicationForm  applicationForm;
}

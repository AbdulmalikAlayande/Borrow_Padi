package com.example.loanapplication.data.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationRequest {
	
	@NotEmpty
	private String userName;
	@NotEmpty
	private long loanAmount;
	@NotEmpty
	private String loanPurpose;
	@NotEmpty
	private String repaymentPreference;
	@NotEmpty
	private String password;
	@NotEmpty
	private String userPin;
	@NotEmpty
	private int loanTenure;
}

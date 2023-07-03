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
	private double loanAmount;
	@NonNull
	private String loanPurpose;
	@NonNull
	private String repaymentPreference;
	@org.springframework.lang.NonNull
	private String password;
	@NotEmpty
	private String userPin;
	@NotEmpty
	private int loanTenure;
}

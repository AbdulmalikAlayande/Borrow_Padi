package com.example.loanapplication.data.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationRequest {
	
	private String userName;
	private double loanAmount;
	private String loanPurpose;
	private String repaymentPreference;
	private String password;
	private String userPin;
	private int loanTenure;
}

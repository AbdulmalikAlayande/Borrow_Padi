package com.example.loanapplication.data.dtos.requests;

import com.example.loanapplication.data.models.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationRequest {
	
	@NotEmpty
	private String userName;
	@NonNull
	private BigDecimal loanAmount;
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

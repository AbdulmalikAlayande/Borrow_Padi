package com.example.loanapplication.data.dtos.responses;

import com.example.loanapplication.data.models.LoanPaymentRecord;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {
	
	private String message;
	private String profileId;
	private boolean profileSetUpState;
	private BigDecimal loanLimit;
	private int loanLevel;
	private LoanPaymentRecord record;
	private String username;
	private boolean hasPendingLoan;
	private String userPin;
}

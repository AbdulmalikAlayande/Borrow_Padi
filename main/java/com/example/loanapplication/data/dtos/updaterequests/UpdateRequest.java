package com.example.loanapplication.data.dtos.updaterequests;

import com.example.loanapplication.data.models.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRequest {
	
	@Nullable
	private String firstName;
	@NotEmpty
	private String username;
	@Nullable
	private String lastName;
	@Nullable
	private String password;
	@Nullable
	private String email;
	@Nullable
	private String phoneNumber;
	@Nullable
	private List<LoanApplicationForm> applicationFormSet;
	@Nullable
	private List<LoanAgreementForm> agreementForms;
}

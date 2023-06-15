package com.example.loanapplication.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
	private String userId;
	private String  agreementFormId;
	private String  applicationFormId;
}

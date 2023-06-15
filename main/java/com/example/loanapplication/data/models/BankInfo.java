package com.example.loanapplication.data.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BankInfo {
	private String name;
	private String accountNumber;
	private String bvn;
	private String bankName;
	
}

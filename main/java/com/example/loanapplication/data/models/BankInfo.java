package com.example.loanapplication.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankInfo {
	private String name;
	private String accountNumber;
	private String bvn;
	private String bankName;
	
}

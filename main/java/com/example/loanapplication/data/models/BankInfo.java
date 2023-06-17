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
public class BankInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bankInfoId;
	private String name;
	private String accountNumber;
	private String bvn;
	private String bankName;
}

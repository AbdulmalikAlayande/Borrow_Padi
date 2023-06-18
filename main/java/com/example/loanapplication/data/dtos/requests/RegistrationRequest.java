package com.example.loanapplication.data.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String phoneNumber;
}

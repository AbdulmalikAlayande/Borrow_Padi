package com.example.loanapplication.data.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileRequest {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String accountName;
	@NotEmpty
	private String accountNumber;
	@NotEmpty
	private String bvn;
	@NotEmpty
	private String bankName;
	@NotEmpty
	private String streetName;
	@NotEmpty
	private String houseNumber;
	@NotEmpty
	private String postCode;
	@NotEmpty
	private String state;
	@NotEmpty
	private String city;
}

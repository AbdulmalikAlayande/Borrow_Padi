package com.example.loanapplication.data.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddressRequest {
	@NotEmpty
	private String streetName;
	@NotEmpty
	private String houseNumber;
	@NonNull
	private String postCode;
	@NotEmpty
	private String state;
	@NotEmpty
	private String city;
}

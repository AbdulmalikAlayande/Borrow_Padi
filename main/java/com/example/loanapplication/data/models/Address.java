package com.example.loanapplication.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Address {
	private String streetName;
	private String houseNumber;
	private String postCode;
	private String state;
	private String city;
}

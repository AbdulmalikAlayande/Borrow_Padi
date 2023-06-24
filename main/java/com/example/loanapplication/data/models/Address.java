package com.example.loanapplication.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String addressId;
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

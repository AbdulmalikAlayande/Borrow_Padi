package com.example.loanapplication.data.models;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Customer extends User{
	
	private String name;
	private String password;
	private String id;
	private String email;
	private String phoneNumber;
}

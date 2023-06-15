package com.example.loanapplication.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Admin extends User{
	private String name;
	private String password;
	private String id;
	private String email;
	private String phoneNumber;
	private String adminCode;
}

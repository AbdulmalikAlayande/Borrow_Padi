package com.example.loanapplication.data.models;

import jakarta.persistence.Id;
import lombok.*;

@Data
@EqualsAndHashCode
public class User {
	private String name;
	private String password;
	@Id
	private String id;
	private String email;
	private String phoneNumber;
}

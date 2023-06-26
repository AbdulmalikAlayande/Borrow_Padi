package com.example.loanapplication.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	@Column(unique = true)
	private String username;
	@NonNull
	private String password;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userId;
	private String email;
	private String phoneNumber;
}

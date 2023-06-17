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
	private String name;
	private String password;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userId;
	@Column(unique = true)
	private String email;
	@NonNull
	@OneToOne(cascade = CascadeType.ALL)
	private BankInfo info;
	@Column(unique = true)
	private String phoneNumber;
}

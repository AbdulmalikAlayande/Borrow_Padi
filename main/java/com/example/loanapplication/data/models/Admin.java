package com.example.loanapplication.data.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Admin{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String adminId;
	@NonNull
	@OneToOne
	private User user;
	@NonNull
	private String adminCode;
}

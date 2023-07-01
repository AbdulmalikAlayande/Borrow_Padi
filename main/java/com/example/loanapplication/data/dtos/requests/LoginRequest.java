package com.example.loanapplication.data.dtos.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
	@Nullable
	private String email;
	@NotEmpty
	private String password;
	@Nullable
	private String username;
}

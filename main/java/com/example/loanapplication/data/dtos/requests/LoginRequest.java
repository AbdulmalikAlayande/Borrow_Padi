package com.example.loanapplication.data.dtos.requests;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class LoginRequest {
	
	private String email;
	private String password;
	private String username;
}

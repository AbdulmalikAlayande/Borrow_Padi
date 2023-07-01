package com.example.loanapplication.data.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoundUserResponse {
	
	private String message;
	private boolean isLoggedIn;
	private String userid;
}

package com.example.loanapplication.data.dtos.updateresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateResponse {
	
	private String id;
	private String message;
	private String username;
}

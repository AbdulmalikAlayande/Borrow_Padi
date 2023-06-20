package com.example.loanapplication.data.dtos.requests;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {
	private String userName;
	private String userEmailAddress;
	private String userPassword;
}

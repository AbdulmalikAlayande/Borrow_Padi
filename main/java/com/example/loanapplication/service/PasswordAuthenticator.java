package com.example.loanapplication.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PasswordAuthenticator extends Authenticator {
	private final String USERNAME = "alaabdulmalik03@gmail.com";
	private final String PASSWORD = "akncjypshgzmqkms";
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(USERNAME, PASSWORD);
	}
}

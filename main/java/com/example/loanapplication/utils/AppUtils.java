package com.example.loanapplication.utils;

import org.apache.kafka.common.Uuid;

import java.util.UUID;

public class AppUtils {
	
	public static String generatedToken(){
		System.out.println(UUID.randomUUID());
		return Uuid.randomUuid().toString();
	}
	
	public static void main(String[] args) {
		System.out.println(AppUtils.generatedToken());
	}
}

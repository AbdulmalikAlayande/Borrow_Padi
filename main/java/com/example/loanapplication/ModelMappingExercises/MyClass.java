package com.example.loanapplication.ModelMappingExercises;

import java.math.BigDecimal;

public class MyClass {
	private String name;
	private BigDecimal amount;
	private int age;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public boolean isTrue() {
		return isTrue;
	}
	
	public void setTrue(boolean aTrue) {
		isTrue = aTrue;
	}
	
	private boolean isTrue;
}

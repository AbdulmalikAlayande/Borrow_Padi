package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Admin;
import com.example.loanapplication.data.models.Customer;
import com.example.loanapplication.data.models.User;

import java.util.Map;
import java.util.Optional;

public abstract class Borrow_PadiUserRepo implements UserRepository{
	@Override
	public Map<String, Optional<User>> findAllUsers() {
		
		return null;
	}
	
	@Override
	public Map<String, Optional<Admin>> findAllAdmin() {
		return null;
	}
	
	@Override
	public Map<String, Optional<Customer>> findAllCustomers() {
		return null;
	}
}

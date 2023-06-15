package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Admin;
import com.example.loanapplication.data.models.Customer;
import com.example.loanapplication.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	default Map<String, Optional<User>> findAllUsers(){
		
		return null;
	}
	default Map<String, Optional<Admin>> findAllAdmin(){
		return null;
	}
	default Map<String, Optional<Customer>> findAllCustomers(){
		return null;
	}
}

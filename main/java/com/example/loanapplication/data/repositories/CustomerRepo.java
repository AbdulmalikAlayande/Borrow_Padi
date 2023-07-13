package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Customer;
import com.example.loanapplication.data.models.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, String> {
	
	Optional<Customer> findByUser(@NonNull User user);
	
	void deleteByUser(@NonNull User user);
}

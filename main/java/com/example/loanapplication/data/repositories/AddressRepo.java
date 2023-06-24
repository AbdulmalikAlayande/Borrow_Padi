package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, String> {
	
	Optional<List<Address>> findAllByPostCode(String s);
}

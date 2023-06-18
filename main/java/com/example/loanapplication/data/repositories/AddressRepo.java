package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, String> {
}

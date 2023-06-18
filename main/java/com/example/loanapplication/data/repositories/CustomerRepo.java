package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepo extends JpaRepository<Customer, String> {
}

package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepo extends JpaRepository<Loan, Long> {
}

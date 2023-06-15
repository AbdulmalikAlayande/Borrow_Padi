package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.LoanApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanAplicationRepo extends JpaRepository<LoanApplicationForm, String> {
}

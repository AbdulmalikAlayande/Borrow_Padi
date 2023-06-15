package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.LoanAgreementForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanAgreementRepo extends JpaRepository<LoanAgreementForm, String> {
}

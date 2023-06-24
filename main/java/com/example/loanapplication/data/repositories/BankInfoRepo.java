package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankInfoRepo extends JpaRepository<BankInfo, Long> {


}

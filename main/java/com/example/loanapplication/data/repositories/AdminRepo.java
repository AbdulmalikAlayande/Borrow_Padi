package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, String> {
}

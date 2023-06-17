package com.example.loanapplication.data.repositories;

import com.example.loanapplication.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}

package com.gokhankorkmaz.loanapplicationsystem.repositories;

import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
}

package com.gokhankorkmaz.loanapplicationsystem.repositories;

import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
    Credit getCreditByCustomer(Customer customer);
}

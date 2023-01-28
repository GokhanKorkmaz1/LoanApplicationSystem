package com.gokhankorkmaz.loanapplicationsystem.repositories;

import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

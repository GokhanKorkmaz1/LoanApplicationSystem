package com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts;

import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;

import java.sql.Date;

public interface CreditBusinessRules {
    Credit ruleForCreditExist(Customer customer);
    Credit CalculateCredit(Customer customer);
}

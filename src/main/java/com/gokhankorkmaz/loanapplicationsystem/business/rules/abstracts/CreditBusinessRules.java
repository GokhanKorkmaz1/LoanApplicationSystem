package com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts;

import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;

public interface CreditBusinessRules {
    Credit ruleForCreditExist(Customer customer);
    Credit calculateCredit(Customer customer);
}

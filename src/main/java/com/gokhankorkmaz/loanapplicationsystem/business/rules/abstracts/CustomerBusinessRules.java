package com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts;

import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;

public interface CustomerBusinessRules {
    Customer ruleForCustomerExist(int id);
    Customer ruleForCustomerExist(String identityNumber);
    void ruleForIdentityNumberIsUnique(String identityNumber);
    boolean ruleForIsMonthlyIncomeOrAssuranceChanged(Customer oldCustomer, Customer newCustomer);


}

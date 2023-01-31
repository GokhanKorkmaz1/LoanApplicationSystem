package com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts;

import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerAddRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;

public interface CustomerBusinessRules {
    Customer ruleForCustomerExist(int id);
    Customer ruleForCustomerExist(String identityNumber);
    void ruleForIdentityNumberIsUnique(String identityNumber);
    CustomerAddRequest ruleForCalculateCreditRating(CustomerRequest customerRequest);
    boolean ruleForIsMonthlyIncomeOrAssuranceChanged(Customer oldCustomer, Customer newCustomer);


}

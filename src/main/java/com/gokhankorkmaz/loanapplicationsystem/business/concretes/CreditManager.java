package com.gokhankorkmaz.loanapplicationsystem.business.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CreditService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CreditRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CreditResponse;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CreditBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CreditRepository;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CreditManager implements CreditService {
    private final CreditRepository creditRepository;
    private final CreditBusinessRules creditBusinessRules;
    private final CustomerRepository customerRepository;
    private final CustomerBusinessRules customerBusinessRules;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CreditManager(CreditRepository creditRepository, CreditBusinessRules creditBusinessRules, CustomerRepository customerRepository, CustomerBusinessRules customerBusinessRules, ModelMapperService modelMapperService) {
        this.creditRepository = creditRepository;
        this.creditBusinessRules = creditBusinessRules;
        this.customerRepository = customerRepository;
        this.customerBusinessRules = customerBusinessRules;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public CreditResponse add(CreditRequest creditRequest) {
        Customer customer = customerBusinessRules.ruleForCustomerExist(creditRequest.getCustomerId());
        Credit credit = this.creditBusinessRules.CalculateCredit(customer);
        Credit creditResult = this.creditRepository.saveAndFlush(credit);
        CreditResponse creditResponse = this.modelMapperService.forDto().map(creditResult, CreditResponse.class);
        return creditResponse;
    }

    @Override
    public CreditResponse getByIdentityNumberAndBirthdate(String identityNumber, Date birthdate) {
        Customer customer = this.customerBusinessRules.ruleForCustomerExist(identityNumber);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        Credit credit = this.creditBusinessRules.ruleForCreditExist(Customer.builder().identityNumber(identityNumber).birthdate(birthdate).build());
        CreditResponse creditResponse = CreditResponse.builder().customerResponse(customerResponse)
                .id(credit.getId()).amount(credit.getAmount()).state(credit.isState()).build();
        return creditResponse;
    }
}

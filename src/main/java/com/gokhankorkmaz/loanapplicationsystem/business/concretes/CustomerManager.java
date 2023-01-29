package com.gokhankorkmaz.loanapplicationsystem.business.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CreditService;
import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CustomerService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CreditRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerBusinessRules customerBusinessRules;
    private final CreditService creditService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerRepository customerRepository, CustomerBusinessRules customerBusinessRules, CreditService creditService, ModelMapperService modelMapperService) {
        this.customerRepository = customerRepository;
        this.customerBusinessRules = customerBusinessRules;
        this.creditService = creditService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public CustomerResponse add(CustomerRequest customerRequest) {
        this.customerBusinessRules.ruleForIdentityNumberIsUnique(customerRequest.getIdentityNumber());

        Customer customer = this.customerRepository.save(modelMapperService.forDto().map(customerRequest, Customer.class));
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);

        // bir customer eklenince otomatik olarak kredi notu işlemi yapılır
        this.creditService.add(CreditRequest.builder().customerId(customerResponse.getId()).build());

        return customerResponse;
    }

    @Override
    public CustomerResponse update(int id, CustomerRequest customerRequest) {

        Customer oldCustomer = this.customerBusinessRules.ruleForCustomerExist(id);
        Customer updatedCustomer = this.modelMapperService.forDto().map(customerRequest, Customer.class);

        // güncellenen kimlik numarası farklıysa sistemdeki diğer kimlik numaraları ile aynı olmamalı
        if(oldCustomer.getIdentityNumber().compareTo(updatedCustomer.getIdentityNumber()) != 0){
            this.customerBusinessRules.ruleForIdentityNumberIsUnique(customerRequest.getIdentityNumber());
        }

        updatedCustomer.setId(id);
        Customer customer = this.customerRepository.save(updatedCustomer);

        // bir customerın aylık geliri veya teminatı güncellenince kredi notu işlemi tekrar yapılır eski işlem tutulur
        if(this.customerBusinessRules.ruleForIsMonthlyIncomeOrAssuranceChanged(oldCustomer, updatedCustomer)){
            this.creditService.add(CreditRequest.builder().customerId(id).build());
        }

        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public CustomerResponse delete(int id) {
        Customer customer = this.customerBusinessRules.ruleForCustomerExist(id);
        this.customerRepository.deleteById(id);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public CustomerResponse getById(int id) {
        Customer customer = this.customerBusinessRules.ruleForCustomerExist(id);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public CustomerResponse getById(String identityNumber) {
        Customer customer = this.customerBusinessRules.ruleForCustomerExist(identityNumber);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerResponse> customerResponses = customers.stream().
                map(c -> this.modelMapperService.forDto().map(c, CustomerResponse.class))
                .collect(Collectors.toList());
        return customerResponses;
    }
}

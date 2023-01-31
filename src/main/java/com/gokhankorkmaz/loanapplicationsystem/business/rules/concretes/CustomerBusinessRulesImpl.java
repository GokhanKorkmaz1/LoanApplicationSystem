package com.gokhankorkmaz.loanapplicationsystem.business.rules.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerAddRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.exceptions.BusinessException;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CustomerBusinessRulesImpl implements CustomerBusinessRules {
    private final CustomerRepository customerRepository;
    private final ModelMapperService modelMapperService;

    public CustomerBusinessRulesImpl(CustomerRepository customerRepository, ModelMapperService modelMapperService) {
        this.customerRepository = customerRepository;
        this.modelMapperService = modelMapperService;
    }

    @SneakyThrows
    @Override
    public Customer ruleForCustomerExist(int id) {
        Customer customer = this.customerRepository.getById(id);
        if(customer == null){
            throw new BusinessException("Customer not found");
        }
        return customer;
    }

    @SneakyThrows
    @Override
    public Customer ruleForCustomerExist(String identityNumber) {
        Customer customer = this.customerRepository.findByIdentityNumber(identityNumber);
        if(customer == null){
            throw new BusinessException("Customer not found");
        }
        return customer;
    }

    @SneakyThrows
    @Override
    public void ruleForIdentityNumberIsUnique(String identityNumber) {
        Customer customer = this.customerRepository.findByIdentityNumber(identityNumber);
        if(customer != null){
            throw new BusinessException("Identity number already registered in the system");
        }
    }

    @Override
    public CustomerAddRequest ruleForCalculateCreditRating(CustomerRequest customerRequest) {
        Random random = new Random();
        CustomerAddRequest customerAddRequest =this.modelMapperService.forDto().map(customerRequest, CustomerAddRequest.class);
        customerAddRequest.setCreditRating(Math.abs(random.nextInt() % 4000));
        return customerAddRequest;
    }

    @Override
    public boolean ruleForIsMonthlyIncomeOrAssuranceChanged(Customer oldCustomer, Customer newCustomer) {
        if(oldCustomer.getMonthlyIncome() == newCustomer.getMonthlyIncome() && oldCustomer.getAssurance() == newCustomer.getAssurance()){
            return false;
        }
        return true;
    }
}

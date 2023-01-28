package com.gokhankorkmaz.loanapplicationsystem.business.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CustomerService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
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
    private final ModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerRepository customerRepository, ModelMapperService modelMapperService) {
        this.customerRepository = customerRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public CustomerResponse add(CustomerRequest customerRequest) {
        // business rules

        Customer customer = this.customerRepository.save(modelMapperService.forDto().map(customerRequest, Customer.class));
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public CustomerResponse update(int id, CustomerRequest customerRequest) {
        // bu customerın olup olmadığını kontrol eden business rule yazılacak
        Customer customer = this.customerRepository.save(modelMapperService.forDto().map(customerRequest, Customer.class));
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public CustomerResponse delete(int id) {
        Customer customer = this.customerRepository.getById(id);
        // bu customerın olup olmadığını kontrol eden business rule yazılacak
        this.customerRepository.deleteById(id);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public CustomerResponse getById(int id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public CustomerResponse getById(String identityNumber) {
        Customer customer = this.customerRepository.findByIdentityNumber(identityNumber);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerResponse> customerResponses = customers.stream().map(c -> this.modelMapperService.forDto().map(c, CustomerResponse.class)).collect(Collectors.toList());
        return customerResponses;
    }
}

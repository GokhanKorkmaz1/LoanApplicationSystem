package com.gokhankorkmaz.loanapplicationsystem.business.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CreditService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerAddRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperManager;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerManagerTest {
    private CustomerManager customerManager;
    private CustomerRepository customerRepository= mock(CustomerRepository.class);
    @Mock
    private CustomerBusinessRules customerBusinessRules;
    @Mock
    private CreditService creditService;
    private ModelMapperService modelMapperService= new ModelMapperManager(new ModelMapper());

    AutoCloseable autoCloseable;
    CustomerRequest customerRequest;
    String identityNumber;
    Random random = new SecureRandom();


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        customerManager = new CustomerManager(customerRepository, customerBusinessRules, creditService, modelMapperService);
        identityNumber = Long.toString(random.nextLong(1000000000,9999999998L));
        customerRequest = CustomerRequest.builder().identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,1)).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void When_addCustomer_Given_CustomerRequest_Expect_Success() {
        CustomerAddRequest customerAddRequest = new CustomerAddRequest();
        customerAddRequest.setCustomerRequest(customerRequest);
        customerAddRequest.setCreditRating(1234);

        when(this.customerBusinessRules.ruleForCalculateCreditRating(customerRequest)).thenReturn(customerAddRequest);

        Customer customer = modelMapperService.forDto().map(customerAddRequest, Customer.class);
        when(customerRepository.save(customer)).thenReturn(customer);

        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        CustomerResponse result = customerManager.add(customerRequest);

        assertEquals(customerResponse.getIdentityNumber(), result.getIdentityNumber());
    }

//    @Test
//    void When_updateCustomer_Given_CustomerRequest_Expect_Success() {
//        Customer oldCustomer = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("testt")
//                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
//                .phoneNumber("1234556789").assurance(20000).build();
//
//        Customer updatedCustomer = this.modelMapperService.forDto().map(customerRequest, Customer.class);
//
//        Customer customer = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("test")
//                .birthdate(new Date(2000,1,1)).monthlyIncome(5000)
//                .phoneNumber("1234556789").assurance(20000).build();
//
//        when(this.customerBusinessRules.ruleForCustomerExist(oldCustomer.getId())).thenReturn(oldCustomer);
//        when(this.customerBusinessRules.ruleForIsMonthlyIncomeOrAssuranceChanged(oldCustomer, updatedCustomer)).thenReturn(false);
//        when(this.customerRepository.save(updatedCustomer)).thenReturn(customer);
//
//        CustomerResponse customerResponse = this.modelMapperService.forDto().map(updatedCustomer, CustomerResponse.class);
//        CustomerResponse result = customerManager.update(1, customerRequest);
//
//        assertEquals(result.getIdentityNumber(), customerResponse.getIdentityNumber());
//    }

    @Test
    void When_deleteCustomer_Given_CustomerId_Expect_Success() {
        Customer customer = Customer.builder().id(1).identityNumber("9999999999").name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();

        when(customerBusinessRules.ruleForCustomerExist(customer.getId())).thenReturn(customer);
        CustomerResponse result = customerManager.delete(customer.getId());

        assertEquals(result.getIdentityNumber(), customer.getIdentityNumber());
    }

    @Test
    void When_GetCustomerById_Given_CustomerId_Expect_GetCustomerById() {
        Customer customer = Customer.builder().id(1).identityNumber("9999999999").name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        when(customerBusinessRules.ruleForCustomerExist(customer.getId())).thenReturn(customer);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        CustomerResponse result = customerManager.getById(customer.getId());
        assertEquals(result.getId(), customerResponse.getId());
    }

    @Test
    void When_GetCustomerById_Given_CustomerIdentityNumber_Expect_GetCustomerByIdentityNumber() {
        Customer customer = Customer.builder().id(1).identityNumber("9999999999").name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        when(customerBusinessRules.ruleForCustomerExist(customer.getIdentityNumber())).thenReturn(customer);
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        CustomerResponse result = customerManager.getById(customer.getIdentityNumber());
        assertEquals(result.getIdentityNumber(), customerResponse.getIdentityNumber());
    }

    @Test
    void When_GetAllCustomer_Expect_GetAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer1 = Customer.builder().id(1).identityNumber("9999999998").name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        Customer customer2 = Customer.builder().id(2).identityNumber("9999999999").name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        customerList.add(customer1);
        customerList.add(customer2);
        List<CustomerResponse> responseList = customerList.stream()
                .map(c -> this.modelMapperService.forDto().map(c, CustomerResponse.class)).toList();

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerResponse> result = this.customerManager.getAll();

        assertEquals(result.size(), responseList.size());
    }
}
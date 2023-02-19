package com.gokhankorkmaz.loanapplicationsystem.business.rules.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CreditService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerAddRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.exceptions.BusinessException;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperManager;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerBusinessRulesImplTest {
    private CustomerBusinessRules customerBusinessRules;
    private CustomerRepository customerRepository= mock(CustomerRepository.class);
    private ModelMapperService modelMapperService= new ModelMapperManager(new ModelMapper());

    AutoCloseable autoCloseable;
    Customer customer;
    String identityNumber;
    Random random = new SecureRandom();

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        customerBusinessRules = new CustomerBusinessRulesImpl(customerRepository, modelMapperService);
        identityNumber = Long.toString(random.nextLong(1000000000,9999999998L));
        customer = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,1)).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void When_getCustomerById_RegisteredId_Expect_Success() {
        when(customerRepository.getReferenceById(customer.getId())).thenReturn(customer);

        Customer result = customerBusinessRules.ruleForCustomerExist(customer.getId());

        assertEquals(result, customer);
    }

    @Test // When_addCustomer_Given_CustomerRequest_Expect_Success
    void When_getCustomerById_NonRegisteredId_Expect_Null() {
        when(customerRepository.getReferenceById(customer.getId())).thenReturn(customer);

        Customer result = customerBusinessRules.ruleForCustomerExist(customer.getId()+1);

        assertNull(result);
    }

    @Test
    void When_getCustomerByIdentityNumber_RegisteredIdentityNumber_Expect_Success() {
        when(customerRepository.findByIdentityNumber(customer.getIdentityNumber())).thenReturn(customer);

        Customer result = customerBusinessRules.ruleForCustomerExist(customer.getIdentityNumber());

        assertEquals(result, customer);
    }

    @Test
    void When_getCustomerByIdentityNumber_NonRegisteredIdentityNumber_Expect_Exception() {
        when(customerRepository.findByIdentityNumber(customer.getIdentityNumber())).thenReturn(customer);

        String expectedMessage = assertThrows(BusinessException.class, () -> {
            customerBusinessRules.ruleForCustomerExist("9999999999");
        }).getMessage();

        assertEquals("Customer not found", expectedMessage);
    }

    @Test
    void When_getIdentityNumberIsUnique_RegisteredIdentityNumber_Expect_Exception() {
        when(customerRepository.findByIdentityNumber(customer.getIdentityNumber())).thenReturn(customer);

        String expectedMessage = assertThrows(BusinessException.class, () -> {
            customerBusinessRules.ruleForIdentityNumberIsUnique(customer.getIdentityNumber());
        }).getMessage();

        assertEquals("Identity number already registered in the system", expectedMessage);
    }

    @Test
    void When_getCalculateCreditRating_Expect_CreditRatingBelow4000() {
        CustomerRequest customerRequest = CustomerRequest.builder().identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,1)).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        CustomerAddRequest result = customerBusinessRules.ruleForCalculateCreditRating(customerRequest);

        assertTrue(result.getCreditRating() < 4000);
    }

    @Test
    void When_getIsMonthlyIncomeOrAssuranceChanged_DifferentMonthlyIncome_Expect_True() {
        Customer newCustomer = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,1)).monthlyIncome(7000)
                .phoneNumber("1234556789").assurance(20000).build();
        Boolean result = customerBusinessRules.ruleForIsMonthlyIncomeOrAssuranceChanged(customer, newCustomer);

        assertEquals(true, result);
    }

    @Test
    void When_getIsMonthlyIncomeOrAssuranceChanged_SameCustomer_Expect_False() {
        Customer sameCustomer = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,1)).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        Boolean result = customerBusinessRules.ruleForIsMonthlyIncomeOrAssuranceChanged(customer, sameCustomer);

        assertEquals(false, result);
    }
}
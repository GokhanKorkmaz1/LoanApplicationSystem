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
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperManager;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditManagerTest {
    private CreditManager creditManager;
    @Mock
    private CreditRepository creditRepository;
    @Mock
    private CreditBusinessRules creditBusinessRules;
    @Mock
    private CustomerBusinessRules customerBusinessRules;
    private ModelMapperService modelMapperService= new ModelMapperManager(new ModelMapper());

    AutoCloseable autoCloseable;
    Customer customer;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        creditManager = new CreditManager(creditRepository, creditBusinessRules, customerBusinessRules, modelMapperService);
        customer = Customer.builder().id(1).identityNumber("9999999999").name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void When_addCredit_Given_CreditRequest_Expect_Success() {
        Credit credit = Credit.builder().id(1).amount(2000).state(true).customer(customer).build();

        when(customerBusinessRules.ruleForCustomerExist(customer.getId())).thenReturn(customer);
        when(creditBusinessRules.calculateCredit(customer)).thenReturn(credit);
        when(creditRepository.save(credit)).thenReturn(credit);
        CreditResponse creditResponse = this.modelMapperService.forDto().map(credit, CreditResponse.class);

        CreditRequest creditRequest = CreditRequest.builder().customerId(1).build();
        CreditResponse result = creditManager.add(creditRequest);

        assertEquals(result, creditResponse);
    }

//    @Test
//    void When_deleteAllCredits_Given_CustomerId_Expect_Success() {
//        List<Credit> credits = new ArrayList<>();
//        Credit credit = Credit.builder().id(1).amount(2000).state(true).customer(customer).build();
//        credits.add(credit);
//
//        when(customerBusinessRules.ruleForCustomerExist(customer.getId())).thenReturn(customer);
//        when(creditRepository.getCreditByCustomer(customer)).thenReturn(credits);
//
//        creditManager.deleteByCustomerId(customer.getId());
//
//        verify(creditManager, times(credits.size())).deleteByCustomerId(customer.getId());
//    }

//    @Test
//    void getByIdentityNumberAndBirthdate() {
//        Credit credit = Credit.builder().id(1).amount(2000).state(true).customer(customer).build();
//        Customer customer1 = Customer.builder().identityNumber
//                (customer.getIdentityNumber()).birthdate(customer.getBirthdate()).build();
//
//        when(customerBusinessRules.ruleForCustomerExist(customer.getId())).thenReturn(customer);
//        when(creditBusinessRules.ruleForCreditExist(customer1)).thenReturn(credit);
//
//        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
//        CreditResponse creditResponse = CreditResponse.builder().customerResponse(customerResponse)
//                .id(credit.getId()).amount(credit.getAmount()).state(credit.isState()).customerResponse(customerResponse).build();
//        CreditResponse result = creditManager.getByIdentityNumberAndBirthdate(customer.getIdentityNumber(), customer.getBirthdate());
//
//        assertEquals(creditResponse, result);
//    }
}
package com.gokhankorkmaz.loanapplicationsystem.business.rules.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CreditBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CreditRepository;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.exceptions.BusinessException;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperManager;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreditBusinessRulesImplTest {
    private CreditBusinessRules creditBusinessRules;
    @Mock
    private CustomerBusinessRules customerBusinessRules;
    private CreditRepository creditRepository= mock(CreditRepository.class);
    AutoCloseable autoCloseable;
    Customer customer;
    String identityNumber;
    Random random = new SecureRandom();

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        creditBusinessRules = new CreditBusinessRulesImpl(customerBusinessRules, creditRepository);
        identityNumber = Long.toString(random.nextLong(1000000000,9999999998L));
        customer = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,1)).monthlyIncome(4000)
                .phoneNumber("1234556789").assurance(20000).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void When_getCreditExist_RegisteredCredit_Expect_Success() {
        List<Credit> credits = new ArrayList<>();
        credits.add(Credit.builder().id(1).state(true).amount(1234).customer(customer).build());

        when(customerBusinessRules.ruleForCustomerExist(customer.getIdentityNumber())).thenReturn(customer);
        when(creditRepository.getCreditByCustomer(customer)).thenReturn(credits);
        Credit credit = creditBusinessRules.ruleForCreditExist(customer);

        assertEquals(credit, credits.get(0));
    }

    @Test
    void When_getCreditExist_NonRegisteredCredit_Expect_Exception() {
        Customer customer1 = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,1)).monthlyIncome(4000)
                .phoneNumber("1234556789").assurance(20000).build();
        when(customerBusinessRules.ruleForCustomerExist(customer.getIdentityNumber())).thenReturn(customer);
        when(creditRepository.getCreditByCustomer(customer)).thenReturn(new ArrayList<Credit>());

        String expectedMessage = assertThrows(BusinessException.class, () -> {
            creditBusinessRules.ruleForCreditExist(customer);
        }).getMessage();

        assertEquals("Credit not found", expectedMessage);
    }

    @Test
    void When_getCreditExist_DifferentIdentityNumberOrBirthdate_Expect_Exception() {
        Customer customer1 = Customer.builder().id(1).identityNumber(identityNumber).name("test").surname("test")
                .birthdate(new Date(2000,1,2)).monthlyIncome(4000)
                .phoneNumber("1234556789").assurance(20000).build();
        when(customerBusinessRules.ruleForCustomerExist(customer.getIdentityNumber())).thenReturn(customer);

        String expectedMessage = assertThrows(BusinessException.class, () -> {
            creditBusinessRules.ruleForCreditExist(customer1);
        }).getMessage();

        assertEquals("Credit not found due to mismatch of identity number and birthdate", expectedMessage);
    }

    @Test
    void When_calculateCredit_CreditRatingBelow500() {
        customer.setCreditRating(400);
        Credit result = creditBusinessRules.calculateCredit(customer);

        assertEquals(0.0, result.getAmount());
        assertEquals(false, result.isState());
    }

    @Test
    void When_calculateCredit_CreditRatingAbove500() {
        customer.setCreditRating(600);
        Credit result = creditBusinessRules.calculateCredit(customer);

        assertTrue(0.0 < result.getAmount());
        assertEquals(true, result.isState());
    }

    @Test
    void When_calculateCredit_CreditRatingBetween500and1000_MonthlyIncomeBelow5000() {
        customer.setCreditRating(600); // Monthly Income 4000 - Assurance 20000
        Credit result = creditBusinessRules.calculateCredit(customer);
        // amount should be 20000 * 0.1 + 10000 -> 12000
        assertEquals(12000.0, result.getAmount());
        assertEquals(true, result.isState());
    }

    @Test
    void When_calculateCredit_CreditRatingBetween500and1000_MonthlyIncomeBetween5000and10000() {
        customer.setCreditRating(600);
        customer.setMonthlyIncome(8000); // Monthly Income 8000 - Assurance 20000
        Credit result = creditBusinessRules.calculateCredit(customer);
        // amount should be 20000 * 0.2 + 20000 -> 24000
        assertEquals(24000.0, result.getAmount());
        assertEquals(true, result.isState());
    }

    @Test
    void When_calculateCredit_CreditRatingBetween500and1000_MonthlyIncomeAbove10000() {
        customer.setCreditRating(600);
        customer.setMonthlyIncome(12000); // Monthly Income 12000 - Assurance 20000
        Credit result = creditBusinessRules.calculateCredit(customer);
        // amount should be 20000 * 0.25 + 12000 * 4 / 2 -> 29000
        assertEquals(29000.0, result.getAmount());
        assertEquals(true, result.isState());
    }

    @Test
    void When_calculateCredit_CreditRatingBelow1000() {
        customer.setCreditRating(1200);
        customer.setMonthlyIncome(12000); // Monthly Income 12000 - Assurance 20000
        Credit result = creditBusinessRules.calculateCredit(customer);
        // amount should be 20000 * 0.5 + 12000 * 4 -> 58000
        assertEquals(58000.0, result.getAmount());
        assertEquals(true, result.isState());
    }
}
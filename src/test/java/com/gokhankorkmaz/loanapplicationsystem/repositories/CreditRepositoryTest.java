package com.gokhankorkmaz.loanapplicationsystem.repositories;

import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CreditRepositoryTest {
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private CustomerRepository customerRepository;
    Credit credit;
    Customer customer;
    Random random = new Random();

    @BeforeEach
    public void setUp() throws Exception {
        String identityNumber = Long.toString(random.nextLong(1000000000,9999999998L));
        customer = Customer.builder().identityNumber(identityNumber).name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        customer = customerRepository.save(customer);
        credit = Credit.builder().amount(12509.2).state(true).customer(customer).build();
        credit = creditRepository.save(credit);
    }

    @AfterEach
    public void tearDown() throws Exception {
        customer = null;
        credit = null;
        creditRepository.deleteAll();
        customerRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void When_getCreditByCustomer_RegisteredCustomer_Expect_findCredit(){
        List<Credit> result = creditRepository.getCreditByCustomer(customer);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(credit);
    }
}

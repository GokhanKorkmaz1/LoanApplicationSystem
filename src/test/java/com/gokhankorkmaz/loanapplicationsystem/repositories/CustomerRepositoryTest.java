package com.gokhankorkmaz.loanapplicationsystem.repositories;

import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    Customer customer;
    String identityNumber;
    Random random = new Random();

    @BeforeEach
    public void setUp() throws Exception {
        identityNumber = Long.toString(random.nextLong(1000000000,9999999998L));
        customer = Customer.builder().identityNumber(identityNumber).name("test").surname("testt")
                .birthdate(new Date(2000,1,1)).creditRating(5000).monthlyIncome(5000)
                .phoneNumber("1234556789").assurance(20000).build();
        customerRepository.save(customer);
    }

    @AfterEach
    public void tearDown() throws Exception {
        customer = null;
        customerRepository.deleteAll();
    }

    // Test case SUCCESS

    @Test
    void When_findByIdentityNumber_RegisteredValue_Expect_findCustomer(){
        Customer result = customerRepository.findByIdentityNumber(identityNumber);
        assertThat(result.getIdentityNumber()).isEqualTo(customer.getIdentityNumber());
    }

    // Test case FAILURE

    @Test
    void When_findByIdentityNumber_NotRegisteredValue_Expect_null(){
        Customer result = customerRepository.findByIdentityNumber("9999999999");
        assertThat(result).isNull();
    }
}

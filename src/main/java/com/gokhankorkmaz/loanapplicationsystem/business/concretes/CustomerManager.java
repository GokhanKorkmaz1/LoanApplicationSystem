package com.gokhankorkmaz.loanapplicationsystem.business.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CreditService;
import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CustomerService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CreditRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerAddRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        CustomerAddRequest customerAddRequest = this.customerBusinessRules.ruleForCalculateCreditRating(customerRequest);
        Customer customer = this.customerRepository.save(modelMapperService.forDto().map(customerAddRequest, Customer.class));

        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        // bir customer eklenince otomatik olarak kredi notu i??lemi yap??l??r
        this.creditService.add(CreditRequest.builder().customerId(customerResponse.getId()).build());
        return customerResponse;
    }

    @Override
    public CustomerResponse update(int id, CustomerRequest customerRequest) {

        Customer oldCustomer = this.customerBusinessRules.ruleForCustomerExist(id);
        Customer updatedCustomer = this.modelMapperService.forDto().map(customerRequest, Customer.class);
        Customer customer;

        // g??ncellenen kimlik numaras?? farkl??ysa sistemdeki di??er kimlik numaralar?? ile ayn?? olmamal??
        if(oldCustomer.getIdentityNumber().compareTo(updatedCustomer.getIdentityNumber()) != 0){
            this.customerBusinessRules.ruleForIdentityNumberIsUnique(customerRequest.getIdentityNumber());
        }

        updatedCustomer.setId(id);
        // G??ncelleme i??lemi m????terinin bilgileri i??indir, credit rating alan?? m????terinin g??ncelleme iste??iyle de??i??ime kapal??d??r.
        updatedCustomer.setCreditRating(oldCustomer.getCreditRating());

        // bir customer??n ayl??k geliri veya teminat?? g??ncellenince kredi notu i??lemi tekrar yap??l??r, eski kay??t sistemde tutulur
        if(this.customerBusinessRules.ruleForIsMonthlyIncomeOrAssuranceChanged(oldCustomer, updatedCustomer)){
            customer = this.customerRepository.save(updatedCustomer);
            this.creditService.add(CreditRequest.builder().customerId(id).build());
        }else{
            customer = this.customerRepository.save(updatedCustomer);
        }

        return this.modelMapperService.forDto().map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse delete(int id) {
        Customer customer = this.customerBusinessRules.ruleForCustomerExist(id);
        this.creditService.deleteByCustomerId(id);
        this.customerRepository.deleteById(id);
        return this.modelMapperService.forDto().map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse getById(int id) {
        Customer customer = this.customerBusinessRules.ruleForCustomerExist(id);
        return this.modelMapperService.forDto().map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse getById(String identityNumber) {
        Customer customer = this.customerBusinessRules.ruleForCustomerExist(identityNumber);
        return this.modelMapperService.forDto().map(customer, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = this.customerRepository.findAll();
        return customers.stream().map(c -> this.modelMapperService.forDto().map(c, CustomerResponse.class)).toList();
    }
}

package com.gokhankorkmaz.loanapplicationsystem.business.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CreditService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CreditRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CreditResponse;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CreditRepository;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CustomerRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.mapping.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CreditManager implements CreditService {
    private final CreditRepository creditRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CreditManager(CreditRepository creditRepository, CustomerRepository customerRepository, ModelMapperService modelMapperService) {
        this.creditRepository = creditRepository;
        this.customerRepository = customerRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public CreditResponse add(CreditRequest creditRequest) {
        // business rule
        Customer customer = customerRepository.getById(creditRequest.getCustomerId());
        Credit credit = new Credit();
        // credit.business rules
        this.creditRepository.save(credit);
        CreditResponse creditResponse = this.modelMapperService.forDto().map(credit, CreditResponse.class);
        return creditResponse;
    }

    @Override
    public CreditResponse getByIdentityNumberAndBirthdate(String identityNumber, Date birthdate) {
        Customer customer = customerRepository.findByIdentityNumber(identityNumber);
        // identity rule birthdate karşılaştırma iş kuralı obje amount ve state içermeli
        CustomerResponse customerResponse = this.modelMapperService.forDto().map(customer, CustomerResponse.class);
        Credit credit = this.creditRepository.getCreditByCustomer(customer);
        // business -> bu sonuç var mı
        CreditResponse creditResponse = CreditResponse.builder().customerResponse(customerResponse)
                .amount(100.0).state(false).build();
        return creditResponse;
    }
}

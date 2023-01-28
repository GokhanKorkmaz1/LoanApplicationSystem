package com.gokhankorkmaz.loanapplicationsystem.business.abstracts;

import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse add(CustomerRequest customerRequest);
    CustomerResponse update(int id,CustomerRequest customerRequest);
    CustomerResponse delete(int id);
    CustomerResponse getById(int id);
    CustomerResponse getById(String identityNumber);
    List<CustomerResponse> getAll();
}

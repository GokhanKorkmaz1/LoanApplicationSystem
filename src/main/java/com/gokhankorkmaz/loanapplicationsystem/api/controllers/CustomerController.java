package com.gokhankorkmaz.loanapplicationsystem.api.controllers;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CustomerService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse getById(@RequestParam int id){
        return this.customerService.getById(id);
    }

    @GetMapping("/getbyidentity")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse getByIdentityNumber(@RequestParam String identityNumber){
        return this.customerService.getById(identityNumber);
    }

    @GetMapping("/getall")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CustomerResponse> getAll(){
        return this.customerService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerResponse add(@Valid @RequestBody CustomerRequest customerRequest){
        return this.customerService.add(customerRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse update(@RequestParam int id, @Valid @RequestBody CustomerRequest customerRequest){
        return this.customerService.update(id, customerRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse delete(@RequestParam int id){
        return this.customerService.delete(id);
    }
}

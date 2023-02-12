package com.gokhankorkmaz.loanapplicationsystem.api.controllers;

import com.gokhankorkmaz.loanapplicationsystem.business.abstracts.CustomerService;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CustomerRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse getById(@PathVariable int id){
        return this.customerService.getById(id);
    }

    @GetMapping("/identity/{identityNumber}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse getByIdentityNumber(@PathVariable String identityNumber){
        return this.customerService.getById(identityNumber);
    }

    @GetMapping("/getall")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CustomerResponse> getAll(){
        return this.customerService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerResponse add(@RequestBody @Valid CustomerRequest customerRequest){
        return this.customerService.add(customerRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse update(@PathVariable int id, @RequestBody @Valid CustomerRequest customerRequest){
        return this.customerService.update(id, customerRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponse delete(@PathVariable int id){
        return this.customerService.delete(id);
    }
}

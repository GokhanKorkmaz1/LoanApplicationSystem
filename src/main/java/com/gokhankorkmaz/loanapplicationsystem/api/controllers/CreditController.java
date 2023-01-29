package com.gokhankorkmaz.loanapplicationsystem.api.controllers;

import com.gokhankorkmaz.loanapplicationsystem.business.concretes.CreditManager;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CreditResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("api/v1/credits")
public class CreditController {
    private final CreditManager creditManager;

    public CreditController(CreditManager creditManager) {
        this.creditManager = creditManager;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CreditResponse getCredit(@RequestParam String identityNumber,@RequestParam Date birthdate){
        return this.creditManager.getByIdentityNumberAndBirthdate(identityNumber, birthdate);
    }
}

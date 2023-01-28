package com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Getter

public class CreditRequest {
    @NotNull
    @Min(1)
    private int customerId;
}

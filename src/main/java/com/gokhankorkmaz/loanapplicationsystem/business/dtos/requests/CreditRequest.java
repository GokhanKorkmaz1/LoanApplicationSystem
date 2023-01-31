package com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditRequest {
    @NotNull(message = "Customer Id shouldn't be null!")
    @Min(value =1, message = "Customer Id should be greater than zero")
    private int customerId;
}
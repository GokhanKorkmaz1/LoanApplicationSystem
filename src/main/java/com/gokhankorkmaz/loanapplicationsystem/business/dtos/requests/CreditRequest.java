package com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@Builder
public class CreditRequest {
    @NotNull
    @Min(1)
    private int customerId;
}

package com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddRequest {
    private CustomerRequest customerRequest;
    @NotNull(message = "Credit rating shouldn't be null!")
    @Min(value = 0, message = "Credit rating should be positive")
    private int creditRating;
}

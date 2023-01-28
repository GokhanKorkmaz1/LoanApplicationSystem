package com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotNull
    @NotBlank
    @Size (min=11, max=11)
    private String identityNumber;
    @NotNull
    @NotBlank
    @Size (min=2, max=50)
    private String name;
    @NotNull
    @NotBlank
    @Size (min=2, max=50)
    private String surname;
    @NotNull
    @NotBlank
    @Size (min=8, max=12)
    private String phoneNumber;
    @NotNull
    @Min(0)
    private double monthlyIncome;
    @NotNull
    private Date birthdate;
    @Min(0)
    private double assurance;
}

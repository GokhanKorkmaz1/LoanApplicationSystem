package com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "Identity number shouldn't be null!")
    @Pattern(regexp = "^\\d+$",message = "Identity number should consist of numbers")
    @Size (min=11, max=11, message = "Identity number length should be 11")
    private String identityNumber;
    @NotBlank(message = "Name shouldn't be null!")
    @Size (min=2, max=50, message = "Name length should be between 2 and 50 characters")
    private String name;
    @NotBlank(message = "Surname shouldn't be null!")
    @Size (min=2, max=50, message = "Surname length should be between 2 and 50 characters")
    private String surname;
    @NotBlank(message = "Phone number shouldn't be null!")
    @Pattern(regexp = "^\\d+$",message = "Phone number should consist of numbers")
    @Size(min=8, max=12, message = "Phone number length should be between 8 and 12")
    private String phoneNumber;
    @NotNull(message = "Monthly income shouldn't be null!")
    @Min(value = 0, message = "Monthly income should be positive")
    private double monthlyIncome;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Birthdate shouldn't be null!")
    private Date birthdate;
    @Min(value = 0, message = "Assurance should be positive")
    private double assurance;
}

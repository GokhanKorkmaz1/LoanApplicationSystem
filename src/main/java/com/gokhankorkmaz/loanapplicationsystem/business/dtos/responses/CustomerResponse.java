package com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private int id;
    private String name;
    private String surname;
    private String identityNumber;
    private String phoneNumber;
    private Date birthdate;

}

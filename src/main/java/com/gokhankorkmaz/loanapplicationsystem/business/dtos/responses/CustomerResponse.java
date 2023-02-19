package com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class CustomerResponse {
    private int id;
    private String name;
    private String surname;
    private String identityNumber;
    private String phoneNumber;
    private Date birthdate;
    private double monthlyIncome;
    private int creditRating;
    private double assurance;

}

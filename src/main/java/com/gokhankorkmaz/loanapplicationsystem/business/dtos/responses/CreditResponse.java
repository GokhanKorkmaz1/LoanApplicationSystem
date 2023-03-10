package com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CreditResponse {
    private int id;
    private boolean state;
    private double amount;
    private CustomerResponse customerResponse;
}

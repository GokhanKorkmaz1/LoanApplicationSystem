package com.gokhankorkmaz.loanapplicationsystem.business.abstracts;

import com.gokhankorkmaz.loanapplicationsystem.business.dtos.requests.CreditRequest;
import com.gokhankorkmaz.loanapplicationsystem.business.dtos.responses.CreditResponse;

import java.sql.Date;

public interface CreditService {
    CreditResponse add(CreditRequest creditRequest);
    CreditResponse getByIdentityNumberAndBirthdate(String identityNumber, Date birthdate);
}

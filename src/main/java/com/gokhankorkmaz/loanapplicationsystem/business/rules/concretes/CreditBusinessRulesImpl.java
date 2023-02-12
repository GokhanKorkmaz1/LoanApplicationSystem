package com.gokhankorkmaz.loanapplicationsystem.business.rules.concretes;

import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CreditBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.business.rules.abstracts.CustomerBusinessRules;
import com.gokhankorkmaz.loanapplicationsystem.entities.Credit;
import com.gokhankorkmaz.loanapplicationsystem.entities.Customer;
import com.gokhankorkmaz.loanapplicationsystem.repositories.CreditRepository;
import com.gokhankorkmaz.loanapplicationsystem.utilities.exceptions.BusinessException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditBusinessRulesImpl implements CreditBusinessRules {
    private final CustomerBusinessRules customerBusinessRules;
    private final CreditRepository creditRepository;

    public CreditBusinessRulesImpl(CustomerBusinessRules customerBusinessRules, CreditRepository creditRepository) {
        this.customerBusinessRules = customerBusinessRules;
        this.creditRepository = creditRepository;
    }

    @SneakyThrows
    @Override
    public Credit ruleForCreditExist(Customer customer) {
        Customer resultCustomer = this.customerBusinessRules.ruleForCustomerExist(customer.getIdentityNumber());

        if(customer.getIdentityNumber().compareTo(resultCustomer.getIdentityNumber()) != 0 || !customer.getBirthdate().equals(resultCustomer.getBirthdate())){
            throw new BusinessException("Credit not found due to mismatch of identity number and birthdate");
        }

        List<Credit> credits = this.creditRepository.getCreditByCustomer(resultCustomer);
        if(credits.isEmpty()){
            throw new BusinessException("Credit not found");
        }
        // müşterinin birden fazla kredi hesabı olabilir, yalnızca son eklenen kullanıcıya gösterilir
        Optional<Credit> optionalCredit = credits.stream().skip(credits.size()-1L).findFirst();
        if(optionalCredit.isEmpty()){
            throw new BusinessException("Credit not found");
        }
        return optionalCredit.get();
    }

    @Override
    public Credit calculateCredit(Customer customer) {
        int creditLimitFactor = 4;
        
        Credit credit = new Credit();
        credit.setCustomer(customer);
        
        if(customer.getCreditRating() < 500){
            credit.setAmount(0);
            credit.setState(false);
        }
        else {
            credit.setState(true);

            if(customer.getCreditRating() >= 500 && customer.getCreditRating() <1000){

                if(customer.getMonthlyIncome() < 5000){
                    credit.setAmount(10000 + customer.getAssurance() * 0.1);
                } else if (customer.getMonthlyIncome() >= 5000 && customer.getMonthlyIncome() < 10000) {
                    credit.setAmount(20000 + customer.getAssurance() * 0.2);
                }else{
                    credit.setAmount(customer.getMonthlyIncome() * creditLimitFactor / 2 + (customer.getAssurance() * 0.25));
                }
            }else {
                credit.setAmount(customer.getMonthlyIncome() * creditLimitFactor + (customer.getAssurance() * 0.5));
            }
        }
        return credit;
    }
}

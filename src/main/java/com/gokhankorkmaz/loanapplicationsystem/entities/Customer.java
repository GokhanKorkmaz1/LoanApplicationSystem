package com.gokhankorkmaz.loanapplicationsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  int id;
    @Column(name = "identity_number", unique = true)
    private String identityNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column(name = "montly_income")
    private double monthlyIncome;
    @Column(name = "credit_rating")
    private int creditRating;
    @Column(name = "assurance")
    private double assurance;
    @OneToMany(mappedBy = "customer")
    private List<Credit> credits;
}

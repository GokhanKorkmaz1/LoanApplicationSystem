package com.gokhankorkmaz.loanapplicationsystem.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "credits")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "state")
    private boolean state;
    @Column(name = "amount")
    private double amount;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}

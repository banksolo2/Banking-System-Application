package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "transaction_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class TransactionStatus {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long transactionStatusId;
    private String name;
    private String code;
}

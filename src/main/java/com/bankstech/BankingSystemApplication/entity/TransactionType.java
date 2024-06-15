package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "transaction_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TransactionType {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long transactionTypeId;
    private String name;
    private String code;
}

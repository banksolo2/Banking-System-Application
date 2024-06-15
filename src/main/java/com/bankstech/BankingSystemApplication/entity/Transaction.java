package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table( name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Transaction {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private String description;
    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "transaction_status_id")
    private TransactionStatus transactionStatus;
    @CreationTimestamp
    private LocalDateTime doneAt;
    @ManyToOne
    @JoinColumn(name = "done_by")
    private User doneBy;
}

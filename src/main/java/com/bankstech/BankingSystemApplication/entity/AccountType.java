package com.bankstech.BankingSystemApplication.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "account_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AccountType {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long accountTypeId;
    private String name;
    private String code;

}

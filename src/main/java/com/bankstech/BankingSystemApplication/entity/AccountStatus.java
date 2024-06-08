package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "account_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AccountStatus {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long accountStatusId;
    private String name;
    private String code;
}

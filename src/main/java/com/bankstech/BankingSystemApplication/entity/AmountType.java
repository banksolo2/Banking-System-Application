package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "amount_types")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class AmountType {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long accountTypeId;
    private String name;
    private String code;
}

package com.bankstech.BankingSystemApplication.model;

import com.bankstech.BankingSystemApplication.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DepositModel {
    private Account account;
    private String depositorName;
    private String phoneNumber;
    private BigDecimal amount;
}

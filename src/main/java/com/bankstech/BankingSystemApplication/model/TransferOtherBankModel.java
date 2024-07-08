package com.bankstech.BankingSystemApplication.model;

import com.bankstech.BankingSystemApplication.entity.Account;
import com.bankstech.BankingSystemApplication.entity.Bank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferOtherBankModel {
    private Account source;
    private String accountNumberDestination;
    private Bank bank;
    private BigDecimal amount;
}

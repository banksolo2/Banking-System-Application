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
public class WithdrawalModel {
    private Account source;
    private BigDecimal amount;
}

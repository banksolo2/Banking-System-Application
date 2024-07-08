package com.bankstech.BankingSystemApplication.model;

import com.bankstech.BankingSystemApplication.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionHistoryModel {
    private Account account;
    private String startDate;
    private String endDate;
}

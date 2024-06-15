package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.TransactionStatus;

public interface TransactionStatusService {

    public TransactionStatus getById(Long transactionStatusId);

    public TransactionStatus getByName(String name);
}

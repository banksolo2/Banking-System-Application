package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.TransactionType;

public interface TransactionTypeService {

    public TransactionType getById(Long transactionTypeId);

    public TransactionType getByName(String name);
}

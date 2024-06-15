package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.AccountType;

import java.util.List;

public interface AccountTypeService {

    public List<AccountType> options(Long accountTypeId);

    public List<AccountType> all();

    public AccountType getById(Long accountTypeId);
}

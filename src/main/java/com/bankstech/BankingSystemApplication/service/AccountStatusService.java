package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.AccountStatus;

import java.util.List;

public interface AccountStatusService {

    public List<AccountStatus> all();

    public List<AccountStatus> options(Long accountTypeId);

    public AccountStatus getByName(String name);
}

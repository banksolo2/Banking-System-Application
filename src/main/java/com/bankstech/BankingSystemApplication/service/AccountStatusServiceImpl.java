package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.AccountStatus;
import com.bankstech.BankingSystemApplication.repository.AccountStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Override
    public List<AccountStatus> all() {
        return accountStatusRepository.findAll();
    }

    @Override
    public List<AccountStatus> options(Long accountTypeId) {
        return accountStatusRepository.options(accountTypeId);
    }
}

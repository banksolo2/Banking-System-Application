package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.AccountType;
import com.bankstech.BankingSystemApplication.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    public List<AccountType> options(Long accountTypeId) {
        return accountTypeRepository.options(accountTypeId);
    }

    @Override
    public List<AccountType> all() {
        return accountTypeRepository.findAll();
    }
}

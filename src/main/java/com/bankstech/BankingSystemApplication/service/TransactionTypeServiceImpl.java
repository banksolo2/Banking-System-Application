package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.TransactionType;
import com.bankstech.BankingSystemApplication.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Override
    public TransactionType getById(Long transactionTypeId) {
        return transactionTypeRepository.findById(transactionTypeId).orElse(new TransactionType());
    }

    @Override
    public TransactionType getByName(String name) {
        return transactionTypeRepository.findByNameIgnoreCase(name).orElse(new TransactionType());
    }
}

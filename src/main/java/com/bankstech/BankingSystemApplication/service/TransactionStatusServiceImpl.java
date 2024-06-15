package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.TransactionStatus;
import com.bankstech.BankingSystemApplication.repository.TransactionStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatusServiceImpl implements TransactionStatusService {
    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @Override
    public TransactionStatus getById(Long transactionStatusId) {
        return transactionStatusRepository.findById(transactionStatusId).orElse(new TransactionStatus());
    }

    @Override
    public TransactionStatus getByName(String name) {
        return transactionStatusRepository.findByNameIgnoreCase(name).orElse(new TransactionStatus());
    }
}

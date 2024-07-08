package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.AmountType;
import com.bankstech.BankingSystemApplication.repository.AmountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmountTypeServiceImpl implements AmountTypeService {
    @Autowired
    private AmountTypeRepository amountTypeRepository;

    @Override
    public AmountType getByName(String name) {
        return amountTypeRepository.findByNameIgnoreCase(name).orElse(new AmountType());
    }
}

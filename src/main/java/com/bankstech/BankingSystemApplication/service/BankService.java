package com.bankstech.BankingSystemApplication.service;


import com.bankstech.BankingSystemApplication.entity.Bank;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.BankModel;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;

import java.util.List;

public interface BankService {

    public List<Bank> all();

    public List<Bank> allDeleted();

    public List<Bank> options(Long bankId);

    public Bank getById(Long bankId);

    public Bank getByName(String name);

    public Boolean isNameExist(String name);

    public Boolean isNameExistOnUpdate(BankModel bankModel);

    public ResponseMessage create(BankModel bankModel, User createdBy);

    public ResponseMessage update(BankModel bankModel, User updatedBy);

    public ResponseMessage delete(Long bankId, User deletedBy);
 }

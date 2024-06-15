package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Account;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.AccountModel;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;

import java.util.List;

public interface AccountService {

    public Account getById(Long accountId);

    public List<Account> getAllAccountByType(String accountTypeName);

    public ResponseMessage create(AccountModel accountModel, User createdBy);

    public Object validateAccount(AccountModel accountModel);

    public ResponseMessage update(AccountModel accountModel, User updatedBy);

    public Account convertToAccount(AccountModel accountModel);

    public Boolean isAccountTypeExist(String email,Long accountTypeId);

    public AccountModel convertToAccountModel(Account account);

}

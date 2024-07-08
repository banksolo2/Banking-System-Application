package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Transaction;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.*;

import java.util.List;

public interface TransactionService {

    public List<Transaction> all();

    public Transaction getById(Long transactionId);

    public List<Transaction> getHistory(TransactionHistoryModel transactionHistoryModel);

    public ResponseMessage withdrawal(WithdrawalModel withdrawalModel, User doneBy);

    public ResponseMessage transfer(TransferModel transferModel,User doneBy);

    public ResponseMessage transferToOtherBank(TransferOtherBankModel transferOtherBankModel,User doneBy);

    public ResponseMessage deposit(DepositModel depositModel,User doneBy);

    public void creditAlert(CreditDebitAlertModel creditDebitAlertModel);

    public void debitAlert(CreditDebitAlertModel creditDebitAlertModel);

}

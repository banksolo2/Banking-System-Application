package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Account;
import com.bankstech.BankingSystemApplication.entity.Transaction;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.*;
import com.bankstech.BankingSystemApplication.repository.AccountRepository;
import com.bankstech.BankingSystemApplication.repository.TransactionRepository;
import com.bankstech.BankingSystemApplication.repository.TransactionStatusRepository;
import com.bankstech.BankingSystemApplication.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionStatusService transactionStatusService;
    @Autowired
    private TransactionTypeService transactionTypeService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AmountTypeService amountTypeService;
    @Autowired
    private TransactionStatusRepository transactionStatusRepository;
    @Autowired
    private DateUtils dateUtils;

    private CreditDebitAlertModel creditDebitAlertModel;

    @Override
    public List<Transaction> all() {
        return transactionRepository.all();
    }

    @Override
    public Transaction getById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElse(new Transaction());
    }

    @Override
    public List<Transaction> getHistory(TransactionHistoryModel transactionHistoryModel) {
        LocalDateTime startDate = LocalDateTime.of(dateUtils.convertToDate(transactionHistoryModel.getStartDate()), LocalTime.of(0,0,1));
        LocalDateTime endDate = LocalDateTime.of(dateUtils.convertToDate(transactionHistoryModel.getEndDate()),LocalTime.of(23,59,59));


        return transactionRepository.findByAccountIdAndDates(transactionHistoryModel.getAccount().getAccountId(), startDate,endDate);
    }

    @Override
    public ResponseMessage withdrawal(WithdrawalModel withdrawalModel, User doneBy) {
        Account account = accountService.getById(withdrawalModel.getSource().getAccountId());
        if(Objects.isNull(withdrawalModel.getSource().getAccountId()) || withdrawalModel.getSource().getAccountId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please select a account number")
                    .build();
        }
        if(Objects.isNull(withdrawalModel.getAmount()) || withdrawalModel.getAmount().equals(BigDecimal.ZERO)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide amount")
                    .build();
        }
        if(account.getBalance().compareTo(withdrawalModel.getAmount()) < 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Account number "+account.getAccountNumber()+" has insufficient fund to complete this transaction")
                    .build();
        }
        BigDecimal newBalance = account.getBalance().subtract(withdrawalModel.getAmount());
        Transaction transaction = Transaction.builder()
                .account(withdrawalModel.getSource())
                .amount(withdrawalModel.getAmount())
                .balance(newBalance)
                .transactionType(transactionTypeService.getByName("Withdrawal"))
                .description("Withdraw by "+account.getFirstName()+" "+account.getMiddleName()+" "+account.getLastName())
                .transactionStatus(transactionStatusService.getByName("successful"))
                .doneBy(doneBy)
                .build();
        transaction = transactionRepository.save(transaction);

        account.setBalance(newBalance);
        account.setUpdatedBy(doneBy);
        account = accountRepository.save(account);

        //send email debit alert
        creditDebitAlertModel = CreditDebitAlertModel.builder()
                .account(account)
                .transaction(transaction)
                .amount(withdrawalModel.getAmount())
                .build();
        debitAlert(creditDebitAlertModel);

        return ResponseMessage.builder()
                .type("success")
                .message("Transaction success")
                .build();
    }

    @Override
    public ResponseMessage transfer(TransferModel transferModel, User doneBy) {
        Account sourceAccount = accountService.getById(transferModel.getSource().getAccountId());
        Account destinationAccount = accountService.getById(transferModel.getDestination().getAccountId());

        if(Objects.isNull(transferModel.getSource().getAccountId()) || transferModel.getSource().getAccountId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide Source Account")
                    .build();
        }
        if(Objects.isNull(transferModel.getDestination().getAccountId()) || transferModel.getDestination().getAccountId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide Destination Account")
                    .build();
        }
        if(transferModel.getSource().getAccountId().equals(transferModel.getDestination().getAccountId())){
            return ResponseMessage.builder()
                    .type("error")
                    .message("You can't make transfer to the same account")
                    .build();
        }
        if(Objects.isNull(transferModel.getAmount()) || transferModel.getAmount().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide amount")
                    .build();
        }
        if(sourceAccount.getBalance().compareTo(transferModel.getAmount()) < 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Account number "+sourceAccount.getAccountNumber()+" has insufficient fund to complete this transaction")
                    .build();
        }


        List<Account> accounts = new ArrayList<>();
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferModel.getAmount()));
        sourceAccount.setUpdatedBy(doneBy);
        accounts.add(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance().add(transferModel.getAmount()));
        destinationAccount.setUpdatedBy(doneBy);
        accounts.add(destinationAccount);
        accounts = accountRepository.saveAll(accounts);

        Account source = accounts.get(0);
        Account destination = accounts.get(1);

        List<Transaction> transactions = new ArrayList<>();
        Transaction t1 = Transaction.builder()
                .account(source)
                .amount(transferModel.getAmount())
                .balance(source.getBalance())
                .amountType(amountTypeService.getByName("debit"))
                .transactionType(transactionTypeService.getByName("transfer"))
                .transactionStatus(transactionStatusService.getByName("successful"))
                .description("Made transfer to "+destination.getFirstName()+" with account number "+destination.getAccountNumber())
                .doneBy(doneBy)
                .build();
        transactions.add(t1);

        Transaction t2= Transaction.builder()
                .account(destination)
                .amount(transferModel.getAmount())
                .balance(destination.getBalance())
                .amountType(amountTypeService.getByName("credit"))
                .transactionType(transactionTypeService.getByName("transfer"))
                .transactionStatus(transactionStatusService.getByName("successful"))
                .description("Transfer made by "+source.getFirstName()+" with account number "+ source.getAccountNumber())
                .doneBy(doneBy)
                .build();
        transactions.add(t2);
        transactions = transactionRepository.saveAll(transactions);

        //send email debit alert
        creditDebitAlertModel = CreditDebitAlertModel.builder()
                .account(source)
                .transaction(transactions.get(0))
                .amount(transferModel.getAmount())
                .build();
        debitAlert(creditDebitAlertModel);

        //send email credit alert
        creditDebitAlertModel = CreditDebitAlertModel.builder()
                .transaction(transactions.get(1))
                .account(destination)
                .amount(transferModel.getAmount())
                .build();
        creditAlert(creditDebitAlertModel);

        return ResponseMessage.builder()
                .type("success")
                .message("Transaction successful")
                .build();
    }

    @Override
    public ResponseMessage transferToOtherBank(TransferOtherBankModel transferOtherBankModel, User doneBy) {

        if(Objects.isNull(transferOtherBankModel.getSource().getAccountId()) || transferOtherBankModel.getSource().getAccountId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide source account number")
                    .build();
        }

        if(Objects.isNull(transferOtherBankModel.getBank().getBankId()) || transferOtherBankModel.getBank().getBankId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide bank info")
                    .build();
        }

        if(Objects.isNull(transferOtherBankModel.getAccountNumberDestination()) || transferOtherBankModel.getAccountNumberDestination().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide destination account number")
                    .build();
        }

        if(transferOtherBankModel.getAccountNumberDestination().length() != 10){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Invalid destination account number")
                    .build();
        }

        if(Objects.isNull(transferOtherBankModel.getAmount()) || transferOtherBankModel.getAmount().compareTo(BigDecimal.ONE) < 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide amount")
                    .build();
        }
        Account account = accountService.getById(transferOtherBankModel.getSource().getAccountId());
        if(account.getBalance().compareTo(transferOtherBankModel.getAmount()) < 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Account number "+account.getAccountNumber()+" has insufficient fund to complete this transaction")
                    .build();
        }

        account.setBalance(account.getBalance().subtract(transferOtherBankModel.getAmount()));
        account.setUpdatedBy(doneBy);
        account = accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .account(account)
                .balance(account.getBalance())
                .transactionType(transactionTypeService.getByName("transfer"))
                .transactionStatus(transactionStatusService.getByName("successful"))
                .amount(transferOtherBankModel.getAmount())
                .amountType(amountTypeService.getByName("debit"))
                .doneBy(doneBy)
                .description("Made transfer to account number "+transferOtherBankModel.getAccountNumberDestination()+" with bank name "+transferOtherBankModel.getBank().getName())
                .build();

        transaction = transactionRepository.save(transaction);

        // send debit email alert
        creditDebitAlertModel = CreditDebitAlertModel.builder()
                .account(account)
                .transaction(transaction)
                .amount(transferOtherBankModel.getAmount())
                .build();
        debitAlert(creditDebitAlertModel);

        return ResponseMessage.builder()
                .type("success")
                .message("Transaction successful")
                .build();
    }

    @Override
    public ResponseMessage deposit(DepositModel depositModel, User doneBy) {
        if(Objects.isNull(depositModel.getAccount().getAccountId()) || depositModel.getAccount().getAccountId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide account number")
                    .build();
        }
        if(Objects.isNull(depositModel.getAmount()) || depositModel.getAmount().compareTo(BigDecimal.ONE) < 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide amount")
                    .build();
        }
        if(Objects.isNull(depositModel.getDepositorName()) || depositModel.getDepositorName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide depositor name")
                    .build();
        }
        if(Objects.isNull(depositModel.getPhoneNumber()) || depositModel.getPhoneNumber().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Please provide phone number")
                    .build();
        }
        Account account = accountService.getById(depositModel.getAccount().getAccountId());
        account.setBalance(account.getBalance().add(depositModel.getAmount()));
        account.setUpdatedBy(doneBy);
        account = accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionType(transactionTypeService.getByName("deposit"))
                .account(depositModel.getAccount())
                .transactionStatus(transactionStatusService.getByName("successful"))
                .amount(depositModel.getAmount())
                .amountType(amountTypeService.getByName("credit"))
                .description("Deposit made by "+depositModel.getDepositorName()+" with phone number "+depositModel.getPhoneNumber())
                .balance(account.getBalance())
                .doneBy(doneBy)
                .build();
        transaction = transactionRepository.save(transaction);

        //send email credit alert
        creditDebitAlertModel = CreditDebitAlertModel.builder()
                .transaction(transaction)
                .account(account)
                .amount(depositModel.getAmount())
                .build();
        creditAlert(creditDebitAlertModel);


        return ResponseMessage.builder()
                .type("success")
                .message("Transaction successful")
                .build();
    }

    @Override
    public void creditAlert(CreditDebitAlertModel creditDebitAlertModel) {

        emailService.sendEmailAlert(
                EmailModel.builder()
                        .recipient(creditDebitAlertModel.getAccount().getEmail())
                        .subject("Credit Transaction Notification")
                        .messageBody(
                                "This is to inform you that a transaction has occurred on your account with details below:\n" +
                                        "Account Name:\t"+ creditDebitAlertModel.getAccount().getFirstName()+" "+creditDebitAlertModel.getAccount().getMiddleName()+" "+creditDebitAlertModel.getAccount().getLastName()+"\n" +
                                        "Account Number:\t"+ creditDebitAlertModel.getAccount().getAccountNumber()+"\n" +
                                        "Account Type:\t"+ creditDebitAlertModel.getAccount().getAccountType().getName()+"\n" +
                                        "Alert Type:\tCREDIT ALERT\n" +
                                        "Transaction Type:\t"+creditDebitAlertModel.getTransaction().getTransactionType().getName()+"\n" +
                                        "Transaction Amount:\t₦"+creditDebitAlertModel.getAmount()+"\n" +
                                        "Current Balance:\t₦"+ creditDebitAlertModel.getAccount().getBalance()
                        )
                        .build()
        );
    }

    @Override
    public void debitAlert(CreditDebitAlertModel creditDebitAlertModel) {
        emailService.sendEmailAlert(
                EmailModel.builder()
                        .recipient(creditDebitAlertModel.getAccount().getEmail())
                        .subject("Debit Transaction Notification")
                        .messageBody(
                                "This is to inform you that a transaction has occurred on your account with details below:\n" +
                                        "Account Name:\t"+ creditDebitAlertModel.getAccount().getFirstName()+" "+creditDebitAlertModel.getAccount().getMiddleName()+" "+creditDebitAlertModel.getAccount().getLastName()+"\n" +
                                        "Account Number:\t"+ creditDebitAlertModel.getAccount().getAccountNumber()+"\n" +
                                        "Account Type:\t"+ creditDebitAlertModel.getAccount().getAccountType().getName()+"\n" +
                                        "Alert Type:\tDEBIT ALERT\n" +
                                        "Transaction Type:\t"+creditDebitAlertModel.getTransaction().getTransactionType().getName()+"\n" +
                                        "Transaction Amount:\t₦"+creditDebitAlertModel.getAmount()+"\n" +
                                        "Current Balance:\t₦"+ creditDebitAlertModel.getAccount().getBalance()
                        )
                        .build()
        );
    }
}

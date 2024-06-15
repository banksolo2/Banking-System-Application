package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Account;
import com.bankstech.BankingSystemApplication.entity.AccountType;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.AccountModel;
import com.bankstech.BankingSystemApplication.model.EmailModel;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.repository.AccountRepository;
import com.bankstech.BankingSystemApplication.utils.AccountUtils;
import com.bankstech.BankingSystemApplication.utils.DateUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountTypeService accountTypeService;
    @Autowired
    private AccountStatusService accountStatusService;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private EmailService emailService;

    @Override
    public Account getById(Long accountId) {
        return accountRepository.findById(accountId).orElse(new Account());
    }

    @Override
    public List<Account> getAllAccountByType(String accountTypeName) {
        return accountRepository.findByAccountType(accountTypeName);
    }

    @Override
    public ResponseMessage create(AccountModel accountModel, User createdBy) {
        Object validateAccountDetails = validateAccount(accountModel);
        if(validateAccountDetails instanceof ResponseMessage){
            return (ResponseMessage) validateAccountDetails;
        }
        if(isAccountTypeExist(accountModel.getEmail(),accountModel.getAccountType().getAccountTypeId())){
            AccountType accountType = accountTypeService.getById(accountModel.getAccountType().getAccountTypeId());
            return ResponseMessage.builder()
                    .type("error")
                    .message("Customer already has a "+accountType.getName()+" account")
                    .build();
        }
        Account account = convertToAccount(accountModel);
        account.setAccountStatus(accountStatusService.getByName("Active"));
        account.setCreatedBy(createdBy);
        account = accountRepository.save(account);

        emailService.sendEmailAlert(
                EmailModel.builder()
                        .recipient(account.getEmail())
                        .subject("NEW ACCOUNT CREATION")
                        .messageBody(
                                "Hi "+account.getFirstName()+",\n\n"+
                                "Congratulations! your account has been successfully created.\n" +
                                        "Your New Account Details\n\n" +
                                        "Account Name:\t"+ account.getFirstName()+" "+account.getMiddleName()+" "+account.getLastName()+"\n" +
                                        "Account Number:\t"+ account.getAccountNumber()+"\n\n" +
                                        "Regards,\n" +
                                        "Banking System APP"
                        )
                        .build()
        );

        return ResponseMessage.builder()
                .type("success")
                .message("Account has been created")
                .build();
    }

    @Override
    public Object validateAccount(AccountModel accountModel) {
        if(Objects.isNull(accountModel.getFirstName()) || accountModel.getFirstName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("First name field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getMiddleName()) || accountModel.getMiddleName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Middle name field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getLastName()) || accountModel.getLastName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Last name field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getAccountType().getAccountTypeId()) || accountModel.getAccountType().getAccountTypeId() == 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Account type field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getEmail()) || accountModel.getEmail().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Email address field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getDateOfBirth()) || accountModel.getDateOfBirth().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Date of Birth field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getGender().getGenderId()) || accountModel.getGender().getGenderId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Gender field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getAddress()) || accountModel.getAddress().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Address field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getStateOfOrigin()) || accountModel.getStateOfOrigin().getStateId().equals(0)){
            return ResponseMessage.builder()
                    .type("error")
                    .message("State of origin field required")
                    .build();
        }
        if(Objects.isNull(accountModel.getPhoneNumber()) || accountModel.getPhoneNumber().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Phone Number field required")
                    .build();
        }

        return "Ok";
    }

    @Override
    public ResponseMessage update(AccountModel accountModel, User updatedBy) {
        Object validateAccountDetails = validateAccount(accountModel);
        if(validateAccountDetails instanceof ResponseMessage){
            return (ResponseMessage) validateAccountDetails;
        }
        Account account = getById(accountModel.getAccountId());
        account.setFirstName(accountModel.getFirstName());
        account.setMiddleName(accountModel.getMiddleName());
        account.setLastName(accountModel.getLastName());
        account.setEmail(accountModel.getEmail());
        account.setPhoneNumber(accountModel.getPhoneNumber());
        account.setAlternativePhoneNumber(accountModel.getAlternativePhoneNumber());
        account.setAccountStatus(accountModel.getAccountStatus());
        account.setGender(accountModel.getGender());
        account.setStateOfOrigin(accountModel.getStateOfOrigin());
        account.setDateOfBirth(dateUtils.convertToDate(accountModel.getDateOfBirth()));
        account.setAddress(accountModel.getAddress());
        account.setUpdatedBy(updatedBy);
        accountRepository.save(account);

        return ResponseMessage.builder()
                .type("success")
                .message("Account details updated")
                .build();
    }

    @Override
    public Account convertToAccount(AccountModel accountModel) {
        return Account.builder()
                .accountStatus(accountModel.getAccountStatus())
                .accountNumber(accountUtils.generateNewAccountNumber())
                .accountType(accountModel.getAccountType())
                .email(accountModel.getEmail())
                .address(accountModel.getAddress())
                .balance(BigDecimal.ZERO)
                .firstName(accountModel.getFirstName())
                .middleName(accountModel.getMiddleName())
                .lastName(accountModel.getLastName())
                .gender(accountModel.getGender())
                .phoneNumber(accountModel.getPhoneNumber())
                .alternativePhoneNumber(accountModel.getAlternativePhoneNumber())
                .dateOfBirth(dateUtils.convertToDate(accountModel.getDateOfBirth()))
                .stateOfOrigin(accountModel.getStateOfOrigin())
                .build();
    }

    @Override
    public Boolean isAccountTypeExist(String email, Long accountTypeId) {
        return accountRepository.isAccountAlreadyHasAccountType(email,accountTypeId);
    }

    @Override
    public AccountModel convertToAccountModel(Account account) {
        return AccountModel.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .accountStatus(account.getAccountStatus())
                .balance(account.getBalance())
                .firstName(account.getFirstName())
                .middleName(account.getMiddleName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .address(account.getAddress())
                .stateOfOrigin(account.getStateOfOrigin())
                .dateOfBirth(dateUtils.convertToString(account.getDateOfBirth()))
                .alternativePhoneNumber(account.getAlternativePhoneNumber())
                .phoneNumber(account.getPhoneNumber())
                .gender(account.getGender())
                .build();
    }
}

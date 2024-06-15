package com.bankstech.BankingSystemApplication.model;

import com.bankstech.BankingSystemApplication.entity.AccountStatus;
import com.bankstech.BankingSystemApplication.entity.AccountType;
import com.bankstech.BankingSystemApplication.entity.Gender;
import com.bankstech.BankingSystemApplication.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountModel {
    private Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private Gender gender;
    private String address;
    private State stateOfOrigin;
    private String phoneNumber;
    private String alternativePhoneNumber;
}

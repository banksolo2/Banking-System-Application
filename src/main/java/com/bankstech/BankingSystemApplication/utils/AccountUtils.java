package com.bankstech.BankingSystemApplication.utils;

import org.springframework.stereotype.Component;

import java.time.Year;


@Component
public class AccountUtils {



    public String generateNewAccountNumber(){
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return String.valueOf(currentYear)+String.valueOf(randNumber);
    }
}

package com.bankstech.BankingSystemApplication.utils;

import org.springframework.stereotype.Component;

@Component
public class ApplicationUtils {

    public String getCode(String value){
        return value.replace(" ", "_").toUpperCase();
    }
}

package com.bankstech.BankingSystemApplication.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class Test {

    public static void main(String[] args){
        System.out.println(new BCryptPasswordEncoder().encode("Banking1234_"));

    }
}

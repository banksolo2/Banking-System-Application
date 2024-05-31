package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Gender;

import java.util.List;

public interface GenderService {

    public Gender getById(Long genderId);

    public List<Gender> options(Long genderId);

    public Gender getByName(String name);
}

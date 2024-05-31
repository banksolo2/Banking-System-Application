package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Gender;
import com.bankstech.BankingSystemApplication.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderRepository genderRepository;


    @Override
    public Gender getById(Long genderId) {
        return genderRepository.findById(genderId).orElse(new Gender());
    }

    @Override
    public List<Gender> options(Long genderId) {
        return genderRepository.options(genderId);
    }

    @Override
    public Gender getByName(String name) {
        return genderRepository.findByNameIgnoreCase(name);
    }
}

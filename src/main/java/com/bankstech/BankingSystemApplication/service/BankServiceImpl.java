package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Bank;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.BankModel;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private UserService userService;

    @Autowired
    private BankRepository bankRepository;

    @Override
    public List<Bank> all() {
        return bankRepository.findAll();
    }

    @Override
    public List<Bank> allDeleted() {
        return bankRepository.findAllDeleted();
    }

    @Override
    public List<Bank> options(Long bankId) {
        return bankRepository.options(bankId);
    }

    @Override
    public Bank getById(Long bankId) {
        return bankRepository.findById(bankId).orElse(new Bank());
    }

    @Override
    public Bank getByName(String name) {
        return bankRepository.findByName(name).orElse(new Bank());
    }

    @Override
    public Boolean isNameExist(String name) {
        return bankRepository.existsByName(name);
    }

    @Override
    public Boolean isNameExistOnUpdate(BankModel bankModel) {
        return bankRepository.existsByNameOnUpdate(bankModel.getBankId(),bankModel.getName());
    }

    @Override
    public ResponseMessage create(BankModel bankModel, User createdBy) {
        if(Objects.isNull(bankModel.getName()) || bankModel.getName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Name field required")
                    .build();
        }
        Bank bank = Bank.builder()
                .name(bankModel.getName())
                .code(bankModel.getName().replace(" ","_").toUpperCase())
                .isDeleted(false)
                .createdBy(createdBy)
                .build();

        bankRepository.save(bank);
        return ResponseMessage.builder()
                .type("success")
                .message("Bank details created")
                .build();
    }

    @Override
    public ResponseMessage update(BankModel bankModel, User updatedBy) {
        Boolean isNameExist = isNameExistOnUpdate(bankModel);
        if(isNameExist){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Bank name already exist")
                    .build();
        }
        Bank bank = getById(bankModel.getBankId());
        bank.setName(bankModel.getName());
        bank.setCode(bankModel.getName().replace(" ","_").toUpperCase());
        bank.setUpdatedBy(updatedBy);
        bankRepository.save(bank);

        return ResponseMessage.builder()
                .type("success")
                .message("Bank details updated")
                .build();
    }

    @Override
    public ResponseMessage delete(Long bankId, User deletedBy) {
        Bank bank = getById(bankId);
        bank.setDeletedBy(deletedBy);
        Clock cl = Clock.systemUTC();
        bank.setDeletedAt(LocalDateTime.now(cl));
        bank.setIsDeleted(true);
        bankRepository.save(bank);

        return ResponseMessage.builder()
                .type("success")
                .message("Bank details deleted")
                .build();
    }
}

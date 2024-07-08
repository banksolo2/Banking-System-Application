package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.AmountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmountTypeRepository extends JpaRepository<AmountType,Long> {

    public Optional<AmountType> findByNameIgnoreCase(String name);
}

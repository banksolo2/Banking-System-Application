package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType,Long> {

    public Optional<TransactionType> findByNameIgnoreCase(String name);
}

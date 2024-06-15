package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Long> {

    public Optional<TransactionStatus> findByNameIgnoreCase(String name);
}

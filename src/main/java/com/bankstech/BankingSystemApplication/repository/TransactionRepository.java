package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("select t from Transaction t where t.account.accountNumber = :accountNumber order by t.doneAt desc")
    public List<Transaction> findByAccountId(@Param("accountNumber")String accountNumber);

    @Query("select t from Transaction t where t.account.accountId = :accountId and t.doneAt between :startDate and :endDate")
    public List<Transaction> findByAccountIdAndDates(
            @Param("accountId")Long accountId,
            @Param("startDate")LocalDateTime startDate,
            @Param("endDate")LocalDateTime endDate
    );


    @Query("select t from Transaction t order by t.doneAt desc")
    public List<Transaction> all();

}

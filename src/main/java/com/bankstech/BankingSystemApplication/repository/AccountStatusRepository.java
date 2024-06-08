package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long> {

    @Query("select a from AccountStatus a order by a.name asc")
    public List<AccountStatus> findAll();

    @Query("select a from AccountStatus a where a.accountStatusId = :accountStatusId order by a.name asc")
    public List<AccountStatus> options(@Param("accountStatusId") Long accountStatusId);
}

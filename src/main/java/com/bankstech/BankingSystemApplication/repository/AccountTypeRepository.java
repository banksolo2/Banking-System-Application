package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType,Long> {

    @Query("select at from AccountType at where at.accountTypeId = :accountTypeId order by at.name asc")
    public List<AccountType> options(@Param("accountTypeId")Long accountTypeId);


    @Query("select at from AccountType at order by at.name asc")
    public List<AccountType> findAll();
}

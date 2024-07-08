package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query("select a from Account a where lower(a.accountType.name) = lower(:accountTypeName) order by a.firstName,a.middleName,a.lastName asc")
    public List<Account> findByAccountType(@Param("accountTypeName")String accountTypeName);

    @Query(
            "select case when count(a) > 0 then true else false end from Account a " +
                    "where a.email = :email and a.accountType.accountTypeId = :accountTypeId "
    )
    public Boolean isAccountAlreadyHasAccountType(@Param("email")String email, @Param("accountTypeId")Long accountTypeId);

    @Query(
            "select a from Account a where lower(a.accountStatus.name) = lower('active') " +
                    "and a.accountId != :accountId " +
                    "order by a.firstName,a.middleName,a.lastName asc"
    )
    public List<Account> options(@Param("accountId")Long accountId);
}
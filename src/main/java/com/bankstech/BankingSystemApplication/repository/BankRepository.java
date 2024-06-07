package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {


    public Optional<Bank> findByName(String name);

    @Query(
            "select case when count(b) > 0 then true else false end from Bank b " +
                    "where b.isDeleted = false and lower(b.name) = lower(:name)"
    )
    public Boolean existsByName(@Param("name")String name);

    @Query(
            "select case when count(b) > 0 then true else false end from Bank b " +
                    "where b.isDeleted = false and b.bankId != :bankId and lower(b.name) = lower(:name)"
    )
    public Boolean existsByNameOnUpdate(@Param("bankId")Long bankId, @Param("name")String name);

    @Query("select b from Bank b where b.isDeleted = false order by b.name asc")
    public List<Bank> findAll();

    @Query("select b from Bank b where b.isDeleted = true order by b.name asc")
    public List<Bank> findAllDeleted();

    @Query("select b from Bank b where b.isDeleted = false and b.bankId != :bankId order by b.name asc")
    public List<Bank> options(@Param("bankId")Long bankId);


}

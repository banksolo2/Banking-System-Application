package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {


    public Gender findByName(String name);

    public Boolean existsByNameIgnoreCase(String name);

    @Query("select g from Gender g where g.genderId != :genderId")
    public List<Gender> options(@Param("genderId") Long genderId);

    public Gender findByNameIgnoreCase(String name);
}

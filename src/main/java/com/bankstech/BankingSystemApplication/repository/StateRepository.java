package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {

    public Boolean existsByNameIgnoreCase(String name);

    @Query(
            "select case when count(s) > 0 then true else false end from State s " +
                    "where s.stateId != :stateId and lower(s.name) = lower(:name)"
    )
    public Boolean existsByNameOnUpdate(@Param("stateId")Long stateId,@Param("name")String name);

    public Optional<State> findByNameIgnoreCase(String name);

    @Query("select s from State s order by s.name asc")
    public List<State> findAll();

    @Query("select s from State s where s.stateId != :stateId order by s.name asc")
    public List<State> options(@Param("stateId")Long stateId);
}

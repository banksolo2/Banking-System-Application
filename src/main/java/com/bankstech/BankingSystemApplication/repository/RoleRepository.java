package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    public Optional<Role> findByName(String name);

    public Boolean existsByNameIgnoreCase(String name);
}

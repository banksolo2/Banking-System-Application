package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmailIgnoreCase(String email);

    public Boolean existsByEmailIgnoreCase(String email);

}

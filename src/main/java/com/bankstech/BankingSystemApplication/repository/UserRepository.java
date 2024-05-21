package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            "select u from User u where u.isDeleted = false and " +
                    "u.isActive = true and lower(u.email) = lower(:email)"
    )
    public Optional<User> findByEmail(@Param("email") String email);

    public Boolean existsByEmailIgnoreCase(String email);

    @Query(
            "select u from User u where u.userId != :userId and u.isDeleted = false " +
            "and u.isActive = true and 'ROLE_DEVELOPER' not in (select ur.name from u.roles ur) and u.email != 'admin@gmail.com' " +
            "order by u.firstName, u.middleName, u.lastName asc"
    )
    public List<User> options(@Param("userId") Long userId);


    @Query(
            "select u from  User u where u.isDeleted = false and 'ROLE_DEVELOPER' " +
                    "not in (select ur.name from u.roles ur) and u.email != 'admin@gmail.com' order by u.firstName, " +
                    "u.middleName, u.lastName asc"
    )
    public List<User> findAll();


    @Query("select u from  User u where u.isDeleted = true order by u.firstName, u.middleName, u.lastName asc")
    public List<User> findAllDeleted();

}

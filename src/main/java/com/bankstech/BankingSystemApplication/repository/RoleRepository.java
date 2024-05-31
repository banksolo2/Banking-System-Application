package com.bankstech.BankingSystemApplication.repository;

import com.bankstech.BankingSystemApplication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    public Optional<Role> findByNameIgnoreCase(String name);

    public Boolean existsByNameIgnoreCase(String name);

    @Query("select r from Role r where r.roleId not in (:roleIds) and upper(r.name) != upper('role_developer') ")
    public List<Role> options(@Param("roleIds") List<Long> roleIds);

    @Query("select r from Role r where r.roleId in (:roleIds)")
    public List<Role> getRoleByIds(@Param("roleIds") List<Long> roleIds);



}

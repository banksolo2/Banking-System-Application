package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Role;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;

import java.util.List;

public interface RoleService {

    public ResponseMessage create(Role role);

    public List<Role> all();

    public ResponseMessage delete(Long roleId);
}

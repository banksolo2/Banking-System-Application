package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.ChangePassword;
import com.bankstech.BankingSystemApplication.model.IsRoles;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;

import java.util.List;

public interface UserService {

    public Boolean isEmailExist(String email);

    public Object validateCreateUser(User user);

    public ResponseMessage create(User user);

    public ResponseMessage update(User user);

    public ResponseMessage delete(Long userId);

    public List<User> options(Long userId);

    public List<User> all();

    public User getByEmail(String email);

    public Boolean isRoleNameExistInUser(User user,String roleName);

    public IsRoles isUserHasRoles(User user);

    public ResponseMessage changePassword(ChangePassword changePassword, User user);
}
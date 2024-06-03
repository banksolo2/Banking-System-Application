package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.*;

import java.util.List;

public interface UserService {

    public Boolean isEmailExist(String email);

    public Object validateCreateUser(CreateUserModel createUserModel);

    public User convertCreateUserModelToUser(CreateUserModel createUserModel);

    public ResponseMessage create(CreateUserModel createUserModel, User createdBy);

    public ResponseMessage update(User user);

    public ResponseMessage delete(Long userId, User deletedBy);

    public List<User> options(Long userId);

    public List<User> all();

    public User getByEmail(String email);

    public Boolean isRoleNameExistInUser(User user,String roleName);

    public IsRoles isUserHasRoles(User user);

    public ResponseMessage changePassword(ChangePassword changePassword, User user);

    public User getById(Long userId);

    public ResponseMessage deleteUserRole(Long roleId, Long userId, User updatedBy);

    public ResponseMessage saveUserRole(AddUserRoleModel addUserRoleModel, User updatedBy);

    public ResponseMessage editUser(UpdateUserModel updateUserModel,User updatedBy);

    public  UpdateUserModel convertUserToUpdateUserModel(User user);

    public Object validateUpdateUserModel(UpdateUserModel updateUserModel);

    public Boolean isEmailExistOnUpdate(Long userId, String email);

    public Object validateUpdateProfileModel(UpdateProfileModel updateProfileModel);

    public UpdateProfileModel convertUserToUpdateProfileModel(User user);

    public ResponseMessage saveUpdateProfileModel(UpdateProfileModel updateProfileModel, User updatedBy);


}

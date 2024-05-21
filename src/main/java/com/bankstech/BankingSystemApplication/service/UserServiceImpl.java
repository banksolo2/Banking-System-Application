package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Role;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.ChangePassword;
import com.bankstech.BankingSystemApplication.model.IsRoles;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean isEmailExist(String email) {
        return null;
    }

    @Override
    public Object validateCreateUser(User user) {
        return null;
    }

    @Override
    public ResponseMessage create(User user) {
        return null;
    }

    @Override
    public ResponseMessage update(User user) {
        return null;
    }

    @Override
    public ResponseMessage delete(Long userId) {
        return null;
    }

    @Override
    public List<User> options(Long userId) {
        return List.of();
    }

    @Override
    public List<User> all() {
        return List.of();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(new User());
    }

    @Override
    public Boolean isRoleNameExistInUser(User user, String roleName) {
        if(Objects.nonNull(user)){
            for(Role r : user.getRoles()){
                if(r.getName().equalsIgnoreCase(roleName)){
                    return true;
                }

            }
        }

        return false;
    }

    @Override
    public IsRoles isUserHasRoles(User user) {
        return IsRoles.builder()
                .isUser(isRoleNameExistInUser(user,"ROLE_USER"))
                .isAdmin(isRoleNameExistInUser(user,"ROLE_ADMIN"))
                .isDeveloper(isRoleNameExistInUser(user,"ROLE_DEVELOPER"))
                .build();
    }

    @Override
    public ResponseMessage changePassword(ChangePassword changePassword, User updatedBy) {
        Boolean isUserExistByEmail = userRepository.existsByEmailIgnoreCase(updatedBy.getEmail());
        User user = updatedBy;
        if(!isUserExistByEmail){
            return ResponseMessage.builder()
                    .type("error")
                    .message("User with email "+updatedBy.getEmail()+" does not exist.")
                    .build();
        }

        if(!changePassword.getPassword().equals(changePassword.getConfirmPassword())){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Password and confirm password don't match")
                    .build();
        }
        user.setPassword(new BCryptPasswordEncoder().encode(changePassword.getPassword()));
        user.setUpdatedBy(updatedBy);

        userRepository.save(user);
        return ResponseMessage.builder()
                .type("success")
                .message("Password updated")
                .build();
    }
}

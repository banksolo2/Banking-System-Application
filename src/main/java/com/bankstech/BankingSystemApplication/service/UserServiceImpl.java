package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Gender;
import com.bankstech.BankingSystemApplication.entity.Role;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.ChangePassword;
import com.bankstech.BankingSystemApplication.model.CreateUserModel;
import com.bankstech.BankingSystemApplication.model.IsRoles;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Value("${app.default-password}")
    private String defaultPassword;

    @Override
    public Boolean isEmailExist(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public Object validateCreateUser(CreateUserModel createUserModel) {
        if(Objects.isNull(createUserModel.getFirstName()) || createUserModel.getFirstName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("First Name field required")
                    .build();
        }
        if(Objects.isNull(createUserModel.getMiddleName()) || createUserModel.getMiddleName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Middle Name field required")
                    .build();
        }
        if(Objects.isNull(createUserModel.getLastName()) || createUserModel.getLastName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Last Name field required")
                    .build();
        }
        if(Objects.isNull(createUserModel.getEmail()) || createUserModel.getEmail().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("email field required")
                    .build();
        }
        if(isEmailExist(createUserModel.getEmail())){
            return ResponseMessage.builder()
                    .type("error")
                    .message("email address "+createUserModel.getEmail()+" already exist.")
                    .build();
        }
        if(Objects.isNull(createUserModel.getPhone()) || createUserModel.getPhone().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Phone number field required")
                    .build();
        }
        if(Objects.isNull(createUserModel.getGender()) || createUserModel.getGender().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Gender field required")
                    .build();
        }
        return "OK";
    }

    @Override
    public User convertCreateUserModelToUser(CreateUserModel createUserModel){
        User user = new User();
        user.setFirstName(createUserModel.getFirstName());
        user.setMiddleName(createUserModel.getMiddleName());
        user.setLastName(createUserModel.getLastName());
        user.setEmail(createUserModel.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(defaultPassword));
        user.setPhone(createUserModel.getPhone());
        user.setIsActive(true);
        user.setIsDeleted(false);
        if(createUserModel.getGender().equalsIgnoreCase("male"))
            user.setGender(Gender.MALE);
        else
            user.setGender(Gender.FEMALE);
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getByName("ROLE_USER"));
        user.setRoles(roles);

        return user;
    }

    @Override
    public ResponseMessage create(CreateUserModel createUserModel, User createdBy) {
        Object validateCreateUserModel = validateCreateUser(createUserModel);
        if(validateCreateUserModel instanceof ResponseMessage){
            return (ResponseMessage) validateCreateUserModel;
        }
        User user = convertCreateUserModelToUser(createUserModel);
        user.setCreatedBy(createdBy);
        userRepository.save(user);

        return ResponseMessage.builder()
                .type("success")
                .message("User has been created.")
                .build();
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

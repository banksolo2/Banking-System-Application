package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Role;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.*;
import com.bankstech.BankingSystemApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GenderService genderService;

    @Value("${app.default-password}")
    private String defaultPassword;

    @Autowired
    private EmailService emailService;

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
        if(Objects.isNull(createUserModel.getGenderId()) || createUserModel.getGenderId() == 0){
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
        user.setGender(genderService.getById(createUserModel.getGenderId()));
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
        user = userRepository.save(user);

        EmailModel emailModel = EmailModel.builder()
                .subject("ACCOUNTING SYSTEM APP: New User Account Create")
                .messageBody(
                        "Hi "+user.getFirstName()+",\n" +
                                "Your user account has been created. " +
                                "please sign in to update your password. Please find login " +
                                "details below:\n\n" +
                                "UserName:\t"+user.getEmail()+"\n" +
                                "Password:\t"+defaultPassword+"\n" +
                                "URL:\thttp://localhost:8080/change-password\n\n" +
                                "Regards,\n" +
                                "Accounting System App"
                )
                .recipient(user.getEmail())
                .build();
        emailService.sendEmailAlert(emailModel);

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
    public ResponseMessage delete(Long userId, User deletedBy) {
        Clock cl = Clock.systemUTC();
        User user = getById(userId);
        user.setIsDeleted(true);
        user.setDeletedBy(deletedBy);
        user.setDeletedAt(LocalDateTime.now(cl));
        userRepository.save(user);

        return ResponseMessage.builder()
                .type("success")
                .message("User has been deleted.")
                .build();
    }

    @Override
    public List<User> options(Long userId) {
        return List.of();
    }

    @Override
    public List<User> all() {
        return userRepository.findAll();
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

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId).orElse(new User());
    }

    @Override
    public ResponseMessage deleteUserRole(Long roleId, Long userId, User updatedBy) {
        User user = getById(userId);
        List<Role> roles = new ArrayList<>();
        for(Role r: user.getRoles()){
            if(!r.getRoleId().equals(roleId)){
                roles.add(r);
            }
        }
        user.setRoles(roles);
        user.setUpdatedBy(updatedBy);
        userRepository.save(user);

        return ResponseMessage.builder()
                .type("success")
                .message("User role has been deleted")
                .build();
    }

    @Override
    public ResponseMessage saveUserRole(AddUserRoleModel addUserRoleModel, User updatedBy) {
        Role userRole = roleService.getByName("ROLE_USER");
        int result = 0;
        for(Role r : addUserRoleModel.getRoles()){
            if(r.getRoleId().equals(userRole.getRoleId())){
                result += 1;
            }
        }
        if(result == 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("User Role cannot be deleted.")
                    .build();
        }

        User user = getById(addUserRoleModel.getUserId());
        user.setUpdatedBy(updatedBy);
        user.setRoles(addUserRoleModel.getRoles());
        userRepository.save(user);

        return ResponseMessage.builder()
                .type("success")
                .message("Roles has been updated")
                .build();
    }

    @Override
    public ResponseMessage editUser(UpdateUserModel updateUserModel, User updatedBy) {
        Object validateUpdateUserModel = validateUpdateUserModel(updateUserModel);
        if(validateUpdateUserModel instanceof ResponseMessage){
            return (ResponseMessage)  validateUpdateUserModel;
        }
        User user = getById(updateUserModel.getUserId());
        user.setFirstName(updateUserModel.getFirstName());
        user.setMiddleName(updateUserModel.getMiddleName());
        user.setLastName(updateUserModel.getLastName());
        user.setEmail(updateUserModel.getEmail());
        user.setPhone(updateUserModel.getPhone());
        user.setGender(updateUserModel.getGender());
        user.setIsActive((updateUserModel.getIsActive().equals("True")) ? true : false);
        user.setUpdatedBy(updatedBy);

        userRepository.save(user);

        return ResponseMessage.builder()
                .type("success")
                .message("User updated.")
                .build();
    }

    @Override
    public UpdateUserModel convertUserToUpdateUserModel(User user) {

        return UpdateUserModel.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .gender(user.getGender())
                .isActive((user.getIsActive() == true) ? "True" : "False")
                .build();
    }

    @Override
    public Object validateUpdateUserModel(UpdateUserModel updateUserModel) {
        if(Objects.isNull(updateUserModel.getUserId()) || updateUserModel.getUserId() == 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("User ID field required")
                    .build();
        }
        if(Objects.isNull(updateUserModel.getMiddleName()) || updateUserModel.getMiddleName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Middle Name field required")
                    .build();
        }
        if(Objects.isNull(updateUserModel.getLastName()) || updateUserModel.getLastName().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Last Name field required")
                    .build();
        }
        if(Objects.isNull(updateUserModel.getEmail()) || updateUserModel.getEmail().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("email field required")
                    .build();
        }
        if(isEmailExistOnUpdate(updateUserModel.getUserId(),updateUserModel.getEmail())){
            return ResponseMessage.builder()
                    .type("error")
                    .message("email address "+updateUserModel.getEmail()+" already exist.")
                    .build();
        }
        if(Objects.isNull(updateUserModel.getPhone()) || updateUserModel.getPhone().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Phone number field required")
                    .build();
        }
        if(Objects.isNull(updateUserModel.getGender()) || updateUserModel.getGender().getGenderId() == 0){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Gender field required")
                    .build();
        }
        if(Objects.isNull(updateUserModel.getIsActive()) || updateUserModel.getIsActive().isEmpty()){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Is Active field required")
                    .build();
        }

        return "Ok";
    }

    @Override
    public Boolean isEmailExistOnUpdate(Long userId, String email) {
        return userRepository.isEmailExistOnUpdate(userId,email);
    }
}

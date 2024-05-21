package com.bankstech.BankingSystemApplication.config;

import com.bankstech.BankingSystemApplication.entity.Gender;
import com.bankstech.BankingSystemApplication.entity.Role;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.repository.RoleRepository;
import com.bankstech.BankingSystemApplication.repository.UserRepository;
import com.bankstech.BankingSystemApplication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.default-password}")
    private String defaultPassword;


    @Override
    public void run(String... args) throws Exception {
        String roleNames[] = {"ROLE_DEVELOPER", "ROLE_ADMIN", "ROLE_USER"};
        List<Role> roles = new ArrayList<>();
        Role r1 = Role.builder().name(roleNames[0]).code("Developer").build();
        if(!roleRepository.existsByNameIgnoreCase(roleNames[0])){
            r1 = roleRepository.save(r1);
        }

        Role r2 = Role.builder().name(roleNames[1]).code("Admin").build();
        if(!roleRepository.existsByNameIgnoreCase(roleNames[1])){
            r2 = roleRepository.save(r2);
        }

        Role r3 = Role.builder().name(roleNames[2]).code("User").build();
        if(!roleRepository.existsByNameIgnoreCase(roleNames[2])){
            r3 = roleRepository.save(r3);
        }

        User user = User.builder()
                .firstName("Oluwaseun")
                .middleName("Joseph")
                .lastName("Olotu")
                .email("seunolo2@gmail.com")
                .phone("+2348080643360")
                .password(new BCryptPasswordEncoder().encode("Olotu1234_"))
                .gender(Gender.MALE)
                .roles(List.of(r1))
                .isActive(true)
                .isDeleted(false)
                .build();

        if(!userRepository.existsByEmailIgnoreCase(user.getEmail())){
            user = userRepository.save(user);
        }

        User user2 = User.builder()
                .firstName("Banking")
                .middleName("System")
                .lastName("App")
                .email("admin@gmail.com")
                .password(new BCryptPasswordEncoder().encode(defaultPassword))
                .gender(Gender.MALE)
                .roles(List.of(r2))
                .isActive(true)
                .isDeleted(false)
                .build();

        if(!userRepository.existsByEmailIgnoreCase(user2.getEmail())){
            user2 = userRepository.save(user2);
        }

    }
}

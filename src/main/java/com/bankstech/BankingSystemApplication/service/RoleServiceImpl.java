package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.entity.Role;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.repository.RoleRepository;
import com.bankstech.BankingSystemApplication.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ApplicationUtils applicationUtils;

    @Override
    public ResponseMessage create(Role role) {
        boolean isRoleNameExist = roleRepository.existsByNameIgnoreCase(role.getName());
        if(isRoleNameExist){
            return ResponseMessage.builder()
                    .type("error")
                    .message("Role name "+role.getName()+" already exist.")
                    .build();
        }
        role.setCode(role.getName());
        role.setName("ROLE_"+applicationUtils.getCode(role.getName()));

        role = roleRepository.save(role);

        return ResponseMessage.builder()
                .type("success")
                .message("Role created.")
                .result(role)
                .build();
    }

    @Override
    public List<Role> all() {
        return roleRepository.findAll();
    }

    @Override
    public ResponseMessage delete(Long roleId) {
        roleRepository.deleteById(roleId);
        return ResponseMessage.builder()
                .type("success")
                .message("Role deleted.")
                .build();
    }

    @Override
    public Role getByCode(String code) {
        return null;
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByNameIgnoreCase(name).orElse(new Role());
    }
}

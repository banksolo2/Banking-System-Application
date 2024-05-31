package com.bankstech.BankingSystemApplication.model;


import com.bankstech.BankingSystemApplication.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddUserRoleModel {
    private Long userId;
    private String userName;
    private List<Role> roles;
}

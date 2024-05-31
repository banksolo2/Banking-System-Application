package com.bankstech.BankingSystemApplication.model;

import com.bankstech.BankingSystemApplication.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateUserModel {

    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private Gender gender;
    private String isActive;
}

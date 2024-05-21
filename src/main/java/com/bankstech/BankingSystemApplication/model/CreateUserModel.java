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
public class CreateUserModel {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
}

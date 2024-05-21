package com.bankstech.BankingSystemApplication.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChangePassword {
    private Long userId;
    private String email;
    private String password;
    private String confirmPassword;
}

package com.bankstech.BankingSystemApplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IsRoles {
    private Boolean isDeveloper;
    private Boolean isAdmin;
    private Boolean isUser;
}

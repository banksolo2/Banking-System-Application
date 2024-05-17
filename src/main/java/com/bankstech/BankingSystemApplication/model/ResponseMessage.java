package com.bankstech.BankingSystemApplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseMessage {
    private String type;
    private String message;
    private Object result;
}

package com.bankstech.BankingSystemApplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmailModel {

    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;

}

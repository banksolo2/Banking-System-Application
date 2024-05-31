package com.bankstech.BankingSystemApplication.service;


import com.bankstech.BankingSystemApplication.model.EmailModel;

public interface EmailService {

    public void sendEmailAlert(EmailModel emailModel);

    public void sendEmailAlertWithAttachment(EmailModel emailModel);

}

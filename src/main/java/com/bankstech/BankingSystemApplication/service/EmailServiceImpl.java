package com.bankstech.BankingSystemApplication.service;

import com.bankstech.BankingSystemApplication.model.EmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmailAddress;
    @Autowired
    private JavaMailSenderImpl mailSender;


    @Override
    public void sendEmailAlert(EmailModel emailModel) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmailAddress);
            mailMessage.setSubject(emailModel.getSubject());
            mailMessage.setTo(emailModel.getRecipient());
            mailMessage.setText(emailModel.getMessageBody());

            javaMailSender.send(mailMessage);
            System.out.println("Mail sent successfully");
        }
        catch (MailException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void sendEmailAlertWithAttachment(EmailModel emailModel) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderEmailAddress);
            mimeMessageHelper.setSubject(emailModel.getSubject());
            mimeMessageHelper.setTo(emailModel.getRecipient());
            mimeMessageHelper.setText(emailModel.getMessageBody());

            FileSystemResource file = new FileSystemResource(new File(emailModel.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            javaMailSender.send(mimeMessage);
            log.info(new StringBuilder().append(file.getFilename()).append(" has been sent to user with email ").append(emailModel.getRecipient()).toString());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

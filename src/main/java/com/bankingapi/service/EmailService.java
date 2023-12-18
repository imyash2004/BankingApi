package com.bankingapi.service;

import com.bankingapi.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${pdf.file.directory}")
    private String pdfFileDirectory;
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public Boolean sendEmail(User user) throws MessagingException {
        String pdfFilePath = pdfFileDirectory + File.separator +"invoice.pdf";
        Boolean b=false;
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        try{
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(user.getUserid());


            mimeMessageHelper.setText("milgya statement apko kya");
            mimeMessageHelper.setSubject("Transaction Details");

            // Attach the PDF file
            FileSystemResource file = new FileSystemResource(pdfFilePath);
            mimeMessageHelper.addAttachment("Transaction.pdf", file);
            javaMailSender.send(mimeMessage);
            System.out.println("sent successfulllllllllllmrnoinpoiuwghipubvutbiutb");
            b=true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return b;

    }
}

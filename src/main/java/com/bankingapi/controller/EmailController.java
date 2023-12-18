package com.bankingapi.controller;

import com.bankingapi.model.User;
import com.bankingapi.service.EmailService;
import com.bankingapi.service.GeneratePdf;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.text.ParseException;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private GeneratePdf generatePdf;
    @RequestMapping("/welcome")
    public String welcome()
    {
        return "hello this is me  Yash";
    }
    @PostMapping("/sendemail")
    public ResponseEntity<?> sendEmail(@RequestBody User user) throws MessagingException, FileNotFoundException, ParseException {
        System.out.println(user);
        this.generatePdf.makingPdf(user);

        if(this.emailService.sendEmail(user)){
            System.out.println("yes ihugouu");
        }
        // this.emailService.sendEmail(emailRequest.getTo());
        return ResponseEntity.ok("domnbiubfurbuboubobo");
    }
}

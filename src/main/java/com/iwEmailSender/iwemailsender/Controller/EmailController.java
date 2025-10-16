package com.iwEmailSender.iwemailsender.Controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content/emailjob")
public class EmailController {
    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/send-email")
    public String sendEmail(){
        try{
            SimpleMailMessage message= new SimpleMailMessage();
            message.setFrom("bezbednostinformaciska@gmail.com");
            message.setTo("simeonristovski90@gmail.com","simeonris9@gmail.com");
            message.setSubject("Simple text");
            message.setText("simeon proba");
            mailSender.send(message);
            return "Successfully";
        }catch (Exception e){
            return e.getMessage();
        }

    }



}

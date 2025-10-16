package com.iwEmailSender.iwemailsender.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoder() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String bCryptPasswordEncoder(String string){
       return bCryptPasswordEncoder.encode(string);
   }


}

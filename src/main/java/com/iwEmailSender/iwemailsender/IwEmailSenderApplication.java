package com.iwEmailSender.iwemailsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRetry
public class IwEmailSenderApplication {
	private static final Logger logger= LoggerFactory.getLogger(IwEmailSenderApplication.class);
	public static void main(String[] args) {
		logger.info("Application is on!");
		SpringApplication.run(IwEmailSenderApplication.class, args);
	}
}

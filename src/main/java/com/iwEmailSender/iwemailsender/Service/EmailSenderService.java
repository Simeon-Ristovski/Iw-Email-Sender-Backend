package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import com.iwEmailSender.iwemailsender.Model.Status;
import com.iwEmailSender.iwemailsender.Repository.ExceptionEntityRepository;
import com.iwEmailSender.iwemailsender.Repository.StatusRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final ExceptionEntityRepository exceptionEntityRepository;
    private final Status statusFAILED;
    private final Status statusSUCCESS;


    public EmailSenderService(JavaMailSender mailSender,StatusRepository statusRepository, ExceptionEntityRepository exceptionEntityRepository) {
        this.mailSender = mailSender;
        this.exceptionEntityRepository = exceptionEntityRepository;
        this.statusFAILED = statusRepository.findByStatusName("FAILED");
        this.statusSUCCESS = statusRepository.findByStatusName("SUCCESS");

    }


    @Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 500))
    public void sendMailOnTime(EmailJob job) throws Exception {
        String[] array = job.getEmailTo().split("\\s*,\\s*");
        for (String s : array) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(job.getEmailFrom());
            if(!job.getNumOfFailedTrys().equals(job.getMaxNumOfTrys())){
                job.setNumOfFailedTrys(job.getNumOfFailedTrys() + 1);
            }
            if (EmailValidator.getInstance().isValid(s)) {
                message.setTo(s);
            } else {
                throw new Exception("Email is not valid");
            }
            if (!job.getSubject().isEmpty()) {
                message.setSubject(job.getSubject());
                if (!job.getMessage().isEmpty()) {
                    message.setText(job.getMessage());
                } else {
                    throw new Exception("Message is empty!");
                }
            } else {
                throw new Exception("Subject is empty!");
            }
//            message.setTo(s);
//            job.setDateDue(job.getDateDue().plusHours(job.getRepetision().getInHours()));
            job.setNextSendTime(job.getNextSendTime().plusMinutes(2));
            job.setModifyAt(LocalDateTime.now());
            job.setModifyBy("SYSTEM");
            mailSender.send(message);
            job.setStatus(statusSUCCESS);
            job.setNumOfFailedTrys(0);
        }
    }

    @Recover
    public void recover(Exception e, EmailJob job) {
        ExceptionEntity exception = new ExceptionEntity(); // add exception to base
        exception.setDateOfException(LocalDateTime.now());
        exception.setId_job(job.getId());
        exception.setUuid(UUID.randomUUID());
        exception.setMessage(e.getMessage());
        exception.setSend(true);
        job.setModifyAt(LocalDateTime.now());
        job.setStatus(statusFAILED);
//      job.setDateDue(job.getDateDue().plusHours(job.getRepetision().getInHours()));
        job.setNextSendTime(job.getNextSendTime().plusMinutes(2));

        exceptionEntityRepository.save(exception);

        SimpleMailMessage messageToAcc = new SimpleMailMessage();
        Account account = job.getSet_by();
        messageToAcc.setText("Please contact the administrator with the following error identifier: " + exception.getUuid() + " .");
        messageToAcc.setTo(account.getEmail());
        messageToAcc.setSubject("Contact the administrator.");
        messageToAcc.setFrom("bezbednostinformaciska@gmail.com");
        mailSender.send(messageToAcc);
    }
}

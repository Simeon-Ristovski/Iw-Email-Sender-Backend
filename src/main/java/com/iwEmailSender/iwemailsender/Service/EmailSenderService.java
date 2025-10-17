package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Model.Account;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import com.iwEmailSender.iwemailsender.Repository.EmailJobRepository;
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
    private final EmailJobRepository emailJobRepository;
    private final StatusRepository statusRepository;
    private final ExceptionEntityRepository exceptionEntityRepository;

    public EmailSenderService(JavaMailSender mailSender, EmailJobRepository emailJobRepository, StatusRepository statusRepository, ExceptionEntityRepository exceptionEntityRepository) {
        this.mailSender = mailSender;
        this.emailJobRepository = emailJobRepository;
        this.statusRepository = statusRepository;
        this.exceptionEntityRepository = exceptionEntityRepository;
    }


    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 500))
    public void sendMailOnTime(EmailJob job) throws Exception {
        String[] array = job.getEmailTo().split("\\s*,\\s*");
        for (String s : array) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(job.getEmailFrom());

            if(EmailValidator.getInstance().isValid(s)){
                message.setTo(s);
            }else {
                throw new Exception("Email is not valid");
            }

            if(!job.getSubject().isEmpty()){
                message.setSubject(job.getSubject());
                if(!job.getMessage().isEmpty()){
                    message.setText(job.getMessage());
                }else {
                    throw new Exception("Message is empty!");
                }
            }else {
                throw new Exception("Subject is empty!");
            }
//            message.setTo(s);
            job.setNumOfFailedTrys(job.getNumOfFailedTrys() + 1);
            mailSender.send(message);
            job.setStatus(statusRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("The status with that id doesn't exist in Database!")));
            job.setDateDue(job.getDateDue().plusHours(job.getRepetision().getInHours()));
            job.setNextSendTime(job.getNextSendTime().plusMinutes(2));
            job.setNumOfFailedTrys(0);
            emailJobRepository.save(job);
        }
    }
    @Recover
    public void recover(Exception e, EmailJob job){
        ExceptionEntity exception = new ExceptionEntity(); // add exception to base
        exception.setDateOfException(LocalDateTime.now());
        exception.setId_job(job.getId());
        exception.setUuid(UUID.randomUUID());
        exception.setMessage(e.getMessage());
        exception.setSend(true);
        job.setNumOfFailedTrys(3);
        job.setStatus(statusRepository.findByStatusName("FAILED"));
        emailJobRepository.save(job);

//        if (!exceptionEntityRepository.existsByMessage(exception.getMessage())) {
            job.setNextSendTime(job.getNextSendTime().plusMinutes(2));
            emailJobRepository.save(job);
            exceptionEntityRepository.save(exception);
//        }

        SimpleMailMessage messageToAcc = new SimpleMailMessage();
        Account account = job.getSet_by();
        messageToAcc.setText("Please contact the administrator with the following error identifier: " + exception.getUuid() + " .");
        messageToAcc.setTo(account.getEmail());
        messageToAcc.setSubject("Contact the administrator.");
        messageToAcc.setFrom("bezbednostinformaciska@gmail.com");
        mailSender.send(messageToAcc);
    }

}

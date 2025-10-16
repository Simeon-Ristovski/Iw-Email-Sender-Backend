package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.EmailJobDto;
import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobDtoInsert;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.EmailJobMapper;
import com.iwEmailSender.iwemailsender.Model.*;
import com.iwEmailSender.iwemailsender.Repository.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmailJobService {
    private final EmailJobRepository emailJobRepository;
    private final AccountRepository accountRepository;
    private final StatusRepository statusRepository;
    private final RepetisionRepository repetisionRepository;
    private final ExceptionEntityRepository exceptionEntityRepository;
    private final JavaMailSender mailSender;
    private final EmailSenderService emailSenderService;
    private Boolean enable = false;


    public EmailJobService(EmailJobRepository emailJobRepository, AccountRepository accountRepository, StatusRepository statusRepository, RepetisionRepository repetisionRepository, ExceptionEntityRepository exceptionEntityRepository, JavaMailSender mailSender, EmailSenderService emailSenderService) {
        this.emailJobRepository = emailJobRepository;
        this.accountRepository = accountRepository;
        this.statusRepository = statusRepository;
        this.repetisionRepository = repetisionRepository;
        this.exceptionEntityRepository = exceptionEntityRepository;
        this.mailSender = mailSender;
        this.emailSenderService = emailSenderService;
    }


    public List<EmailJobDto> findAll() {
        List<EmailJobDto> list = new ArrayList<>();
        for (EmailJob job : emailJobRepository.findAll()) {
            EmailJobDto emailJobDto = EmailJobMapper.INSTANCE.mapEmailJobToDto(job);
            list.add(emailJobDto);
        }
        return list;
    }

    public EmailJobDto findById(Long id) {
        return EmailJobMapper.INSTANCE.mapEmailJobToDto(emailJobRepository.findById(id).orElseThrow());
    }

    public void addEmailJob(Long id_acc, EmailJobDtoInsert emailJobDtoInsert) {
        if (accountRepository.existsAccountById(id_acc)) {
            Account account = accountRepository.findById(id_acc).orElseThrow(() -> new ResourceNotFoundException("The account with that id doesn't exist in Database!"));
            Status status = statusRepository.findByStatusName("FAILED");
            EmailJob job = EmailJobMapper.INSTANCE.mapDtoInsertToEmailJob(emailJobDtoInsert);
            job.setUuid(UUID.randomUUID());
            job.setSet_by(account);
            job.setMaxNumOfTrys(3); //Change to 3
            job.setNumOfFailedTrys(0);
            job.setCreatedAt(LocalDateTime.now());
            job.setCreatedBy(account.getFirstName());
            job.setModifyAt(LocalDateTime.now());
            job.setModifyBy(account.getFirstName());
            job.setStatus(status);
            job.setState(State.ENABLE);
            job.setActive(true);

            job.setRepetision(repetisionRepository.findByRepetisionName(job.getRepetision().toString()));
            LocalDateTime dateTime = job.getDateSend();    // LocalDateTime
            LocalTime time = job.getTimeToSent();          // LocalTime
            job.setNextSendTime(LocalDateTime.of(dateTime.toLocalDate(), time));
            emailJobRepository.save(job);
        } else {
            throw new ResourceNotFoundException("The account with that id doesn't exist in Database!");
        }
    }

    public EmailJob copyEmailJob(EmailJob emailJob, EmailJob job) {
        Status status = statusRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("The status with that id doesn't exist in Database!"));
        emailJob.setStatus(status);
        emailJob.setSubject(job.getSubject());
        emailJob.setMessage(job.getMessage());
        emailJob.setEmailFrom(job.getEmailFrom());
        emailJob.setEmailTo(job.getEmailTo());
        emailJob.setDateSend(job.getDateSend());
        emailJob.setDateDue(job.getDateDue());
        emailJob.setTimeToSent(job.getTimeToSent());
        emailJob.setRepetision(job.getRepetision());
        emailJob.setState(job.getState());
        emailJob.setRepetitive(job.getRepetitive());
        return emailJob;
    }

    public void editEmailJob(Long id_acc, Long id_email, EmailJobDtoInsert jobDtoInsert) {
        if (emailJobRepository.existsById(id_email)) {
            EmailJob emailJob = emailJobRepository.findById(id_email).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
            if (accountRepository.existsAccountById(id_acc)) {
                EmailJob job = EmailJobMapper.INSTANCE.mapDtoInsertToEmailJob(jobDtoInsert);
                Account account = accountRepository.findById(id_acc).orElseThrow(() -> new ResourceNotFoundException("The account with that id doesn't exist in Database!"));
                emailJob.setModifyAt(LocalDateTime.now());
                emailJob.setModifyBy(account.getFirstName());
                emailJob.setSet_by(account);
                emailJob = copyEmailJob(emailJob, job);
                LocalDateTime dateTime = job.getDateSend();
                LocalTime time = job.getTimeToSent();
                emailJob.setNextSendTime(LocalDateTime.of(dateTime.toLocalDate(), time));
                emailJob.setState(State.ENABLE);
                emailJob.setRepetision(repetisionRepository.findByRepetisionName(jobDtoInsert.getRepetision().getRepetisionName()));
                emailJobRepository.save(emailJob);
            } else {
                throw new ResourceNotFoundException("The account with that id doesn't exist in Database!");
            }
        } else {
            throw new ResourceNotFoundException("The job with that id doesn't exist in Database!");
        }
    }

    /**
     * If you want to repeat the same email with the same parameters
     */
    public void repeatTheSameEmailJob(long id_acc, long id_job) {
        if (emailJobRepository.existsById(id_job)) {
            EmailJob original = emailJobRepository.findById(id_job).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
            if (accountRepository.existsAccountById(id_acc)) {
                Account account = accountRepository.findById(id_acc).orElseThrow(() -> new ResourceNotFoundException("The account with that id doesn't exist in Database!"));
                EmailJob copy = new EmailJob();
                copy = copyEmailJob(copy, original);
                copy.setUuid(UUID.randomUUID());
                copy.setModifyAt(LocalDateTime.now());
                copy.setModifyBy(account.getFirstName());
                copy.setSet_by(account);
                copy.setCreatedAt(original.getCreatedAt());
                copy.setCreatedBy(original.getCreatedBy());
                copy.setState(original.getState());
                copy.setRepetitive(original.getRepetitive());
                copy.setMaxNumOfTrys(3);
                copy.setNumOfFailedTrys(original.getNumOfFailedTrys());
                copy.setActive(true);
                copy.setTimeToSent(LocalTime.now().plusMinutes(1));
                copy.setNextSendTime(LocalDateTime.now().plusMinutes(1));
                emailJobRepository.save(copy);
            } else {
                throw new ResourceNotFoundException("The account with that id doesn't exist in Database!");
            }

        } else {
            throw new ResourceNotFoundException("The job with that id doesn't exist in Database!");
        }

    }

    public void deleteEmailJob(Long id) {
        if (emailJobRepository.existsById(id)) {
            EmailJob emailJob = emailJobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
            emailJobRepository.delete(emailJob);
        } else {
            throw new ResourceNotFoundException("The job with that id doesn't exist in Database!");
        }
    }

    public void deleteAllEmailJobs() {
        if(!emailJobRepository.findAll().isEmpty()) {
            for (EmailJob emailJob : emailJobRepository.findAll()) {
                deleteEmailJob(emailJob.getId());
            }
        }else {
            throw new ResourceNotFoundException("No email jobs in Database!");
        }
    }

    public void enableScheduler() {
        enable = true;
    }

    public void disableScheduler() {
        enable = false;
    }

    public void enableOrDisabled() {
        if (!enable) {
            enable = true;
        }
    }

    /**
     * Scheduled method executing every 60sec, sending mails depending on the properties
     */
    @Scheduled(fixedDelay = 60000)
    public void sendEmail() {
        if (!enable) return;

        for (EmailJob emailJob : emailJobRepository.findEmailJobsForSendingNow(LocalDateTime.now(),LocalDateTime.now().plusMinutes(1))) {
            if (emailJob.getRepetitive().name().equals("REPETITIVE")) {
                emailSenderService.sendMailOnTime(emailJob);
            } else {
                emailSenderService.sendMailOnTime(emailJob);
                emailJob.setActive(false);
            }
            if(emailJob.getNextSendTime().isAfter(emailJob.getDateDue())){
                emailJob.setActive(false);
            }


            if (emailJob.getMaxNumOfTrys().equals(emailJob.getNumOfFailedTrys())) {
                SimpleMailMessage message = new SimpleMailMessage();
                if (exceptionEntityRepository.findJobByIdOrderByDateSendDesc(emailJob.getId()).getFirst() !=null) {
                    ExceptionEntity exception = new ExceptionEntity();
                    if (exceptionEntityRepository.numOfExceptionsWithSameIdJob(emailJob.getId()) > 0) {
                        exception = exceptionEntityRepository.findJobByIdOrderByDateSendDesc(emailJob.getId()).getFirst();
                    }
                    if (exception.isSend()) {
                        for (Account allAdministrator : accountRepository.findAllAdministrators()) {
                            message.setSubject("Exception from user");
                            message.setText("Exception id: " + exception.getUuid() + "\n" +
                                    "Exception message: " + exception.getMessage() + "\n" +
                                    "Email job id from exception: " + exception.getId_job() + "\n" +
                                    "Date of exception: " + exception.getDateOfException());
                            message.setFrom("bezbednostinformaciska@gmail.com");
                            message.setTo(allAdministrator.getEmail());
                            mailSender.send(message);
                            emailJob.setNumOfFailedTrys(0);
                        }
                        exception.setSend(false);
                    }
                }
            }
            emailJobRepository.save(emailJob); // TODO: Making plus queries for saving change something!!!
        }









//
//
//
//        for (EmailJob job : emailJobRepository.getEmailJobActive()) {
//            LocalDateTime now = LocalDateTime.now();
//
//            if (now.isBefore(job.getDateDue()) && now.isAfter(job.getDateSend())) {   // TODO (dateSend,dateDue)
//
//                if (now.isAfter(job.getNextSendTime()) && now.isBefore(job.getNextSendTime().plusMinutes(1))) {  //TODO (HOURLY,DAILY,WEEKLY,MONTHLY,YEARLY)
//
//                    if (job.getRepetitive().name().equals("REPETITIVE")) {
//                        emailSenderService.sendMailOnTime(job);
//                    } else {
//                        emailSenderService.sendMailOnTime(job);
//                        job.setActive(false);
//                    }
//
//                }
//            } else {
//                job.setActive(false);
//            }
//
//            if (job.getMaxNumOfTrys().equals(job.getNumOfFailedTrys())) {
//                SimpleMailMessage message = new SimpleMailMessage();
//                if (!exceptionEntityRepository.findAll().isEmpty()) {
////                      ExceptionEntity exception = exceptionEntityRepository.findByIdOfJob(job.getId());
//                    ExceptionEntity exception = new ExceptionEntity();
////                            exceptionEntityRepository.findJobByIdOrderByDateSendDesc(job.getId()).getFirst();
//                    if (exceptionEntityRepository.numOfExceptionsWithSameIdJob(job.getId()) > 0) {
//                        exception = exceptionEntityRepository.findJobByIdOrderByDateSendDesc(job.getId()).getFirst();
//                    }
//                    if (exception.isSend()) {
//                        for (Account allAdministrator : accountRepository.findAllAdministrators()) {
//                            message.setSubject("Exception from user");
//                            message.setText("Exception id: " + exception.getUuid() + "\n" +
//                                    "Exception message: " + exception.getMessage() + "\n" +
//                                    "Email job id from exception: " + exception.getId_job() + "\n" +
//                                    "Date of exception: " + exception.getDateOfException());//TODO: Add message from exception   DONE!!!
//                            message.setFrom("bezbednostinformaciska@gmail.com");
//                            message.setTo(allAdministrator.getEmail());
//                            mailSender.send(message);
//                            job.setNumOfFailedTrys(0);
//                        }
//                        exception.setSend(false);
//                    }
//                }
//            }
//            emailJobRepository.save(job);
//        }
    }
}
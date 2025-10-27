package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobTimeToSendNextInsertDto;
import com.iwEmailSender.iwemailsender.Dto.Input.IsActiveInsert;
import com.iwEmailSender.iwemailsender.Dto.Input.MaxNumOfTriesInsert;
import com.iwEmailSender.iwemailsender.Dto.Output.EmailJobDto;
import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobDtoInsert;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.EmailJobMapper;
import com.iwEmailSender.iwemailsender.Model.*;
import com.iwEmailSender.iwemailsender.Repository.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmailJobService {
    private static final Logger logger = LoggerFactory.getLogger(EmailJobService.class);
    private final EmailJobRepository emailJobRepository;
    private final AccountRepository accountRepository;
    private final StatusRepository statusRepository;
    private final RepetitionRepository repetitionRepository;
    private final ExceptionEntityRepository exceptionEntityRepository;
    private final JavaMailSender mailSender;
    private final EmailSenderService emailSenderService;
    private Boolean enable = false;


    public EmailJobService(EmailJobRepository emailJobRepository, AccountRepository accountRepository, StatusRepository statusRepository, RepetitionRepository repetitionRepository, ExceptionEntityRepository exceptionEntityRepository, JavaMailSender mailSender, EmailSenderService emailSenderService) {
        this.emailJobRepository = emailJobRepository;
        this.accountRepository = accountRepository;
        this.statusRepository = statusRepository;
        this.repetitionRepository = repetitionRepository;
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

    public void addEmailJob(Long id_acc, EmailJobDtoInsert emailJobDtoInsert) throws BadRequestException {
        if (accountRepository.existsAccountById(id_acc)) {
            if (!emailJobDtoInsert.getDateSend().isBefore(LocalDate.now().atStartOfDay())) {
                if (!emailJobDtoInsert.getDateDue().isBefore(LocalDateTime.now())) {
                    Account account = accountRepository.findById(id_acc).orElseThrow(() -> new ResourceNotFoundException("The account with that id doesn't exist in Database!"));
                    Status status = statusRepository.findByStatusName("FAILED");
                    EmailJob job = EmailJobMapper.INSTANCE.mapDtoInsertToEmailJob(emailJobDtoInsert);
                    job.setEmailFrom("bezbednostinformaciska@gmail.com");
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

                    job.setRepetition(repetitionRepository.findByRepetitionName(job.getRepetition().toString()));
                    LocalDateTime dateTime = job.getDateSend();    // LocalDateTime
                    LocalTime time = job.getTimeToSent();          // LocalTime
                    job.setNextSendTime(LocalDateTime.of(dateTime.toLocalDate(), time));

                    logger.info("Email successfully added!");

                    emailJobRepository.save(job);
                } else {
                    logger.error("Date due is in the past!");
                    throw new BadRequestException("Date due is in the past!");
                }
            } else {
                logger.error("Date send is in the past!");
                throw new BadRequestException("Date send is in the past!");
            }
        } else {
            logger.error("The account with that id doesn't exist in Database!");
            throw new ResourceNotFoundException("The account with that id doesn't exist in Database!");
        }
    }

    public void editMaxNumOfTrys(Long id, MaxNumOfTriesInsert maxNumOfTriesInsert) throws BadRequestException {
        EmailJob emailJob = emailJobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
        if (maxNumOfTriesInsert.getMaxNumOfTrys() != null) {
            if (maxNumOfTriesInsert.getMaxNumOfTrys() > 0 && maxNumOfTriesInsert.getMaxNumOfTrys() <= 5) {
                emailJob.setMaxNumOfTrys(maxNumOfTriesInsert.getMaxNumOfTrys());
                emailJob.setActive(true);

                logger.info("Email successfully edited!");

                emailJobRepository.save(emailJob);
            } else {
                logger.error("You can’t use this number. Please choose a number between 1 and 5, including 1 and 5.");
                throw new IllegalArgumentException("You can’t use this number. Please choose a number between 1 and 5, including 1 and 5.");
            }
        } else {
            logger.error("Field for maximum number of tries cannot be empty!");
            throw new BadRequestException("Field for maximum number of tries cannot be empty!");
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
        emailJob.setRepetition(job.getRepetition());
        emailJob.setState(job.getState());
        emailJob.setRepetitive(job.getRepetitive());
        return emailJob;
    }

    public void editEmailJob(Long id_acc, Long id_email, EmailJobDtoInsert jobDtoInsert) throws BadRequestException {
        if (emailJobRepository.existsById(id_email)) {
            EmailJob emailJob = emailJobRepository.findById(id_email).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
            if (accountRepository.existsAccountById(id_acc)) {
                if (!jobDtoInsert.getDateSend().isBefore(LocalDate.now().atStartOfDay())) {
                    if (!jobDtoInsert.getDateDue().isBefore(LocalDateTime.now())) {
                        EmailJob job = EmailJobMapper.INSTANCE.mapDtoInsertToEmailJob(jobDtoInsert);
                        Account account = accountRepository.findById(id_acc).orElseThrow(() -> new ResourceNotFoundException("The account with that id doesn't exist in Database!"));
                        emailJob.setModifyAt(LocalDateTime.now());
                        job.setEmailFrom("bezbednostinformaciska@gmail.com");
                        emailJob.setModifyBy(account.getFirstName());
                        emailJob.setSet_by(account);
                        emailJob = copyEmailJob(emailJob, job);
                        LocalDateTime dateTime = job.getDateSend();
                        LocalTime time = job.getTimeToSent();
                        emailJob.setNextSendTime(LocalDateTime.of(dateTime.toLocalDate(), time));
                        emailJob.setState(State.ENABLE);
                        emailJob.setRepetition(repetitionRepository.findByRepetitionName(jobDtoInsert.getRepetition().getRepetitionName()));
                        emailJob.setActive(true);

                        logger.info("Email successfully edited!");

                        emailJobRepository.save(emailJob);
                    } else {
                        logger.error("Date due is in the past!");
                        throw new BadRequestException("Date due is in the past!");
                    }
                } else {
                    logger.error("Date send is in the past!");
                    throw new BadRequestException("Date send is in the past!");
                }

            } else {
                logger.error("The account with that id doesn't exist in Database!");
                throw new ResourceNotFoundException("The account with that id doesn't exist in Database!");
            }
        } else {
            logger.error("The job with that id doesn't exist in Database!");
            throw new ResourceNotFoundException("The job with that id doesn't exist in Database!");
        }
    }

    /**
     * If you want to repeat the same email with the same parameters
     */
    public void repeatTheSameEmailJob(long id_acc, long id_job, EmailJobTimeToSendNextInsertDto emailJobTimeToSendNextInsertDto) throws BadRequestException {
        if (emailJobRepository.existsById(id_job)) {
            EmailJob original = emailJobRepository.findById(id_job).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
            if (accountRepository.existsAccountById(id_acc)) {
                if (!emailJobTimeToSendNextInsertDto.getDateSend().isBefore(LocalDate.now().atStartOfDay())) {
                    if (!emailJobTimeToSendNextInsertDto.getDateDue().isBefore(LocalDateTime.now())) {
                        Account account = accountRepository.findById(id_acc).orElseThrow(() -> new ResourceNotFoundException("The account with that id doesn't exist in Database!"));

                        EmailJob copy = new EmailJob();

                        copy = copyEmailJob(copy, original);
                        copy.setEmailFrom("bezbednostinformaciska@gmail.com");
                        copy.setDateDue(emailJobTimeToSendNextInsertDto.getDateDue());
                        copy.setDateSend(emailJobTimeToSendNextInsertDto.getDateSend());
                        copy.setTimeToSent(emailJobTimeToSendNextInsertDto.getTimeToSent());
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
                        LocalDateTime dateTime = copy.getDateSend();    // LocalDateTime
                        LocalTime time = copy.getTimeToSent();          // LocalTime
                        copy.setNextSendTime(LocalDateTime.of(dateTime.toLocalDate(), time));

                        logger.info("Email successfully repeated!");

                        emailJobRepository.save(copy);
                    } else {
                        logger.error("Date due is in the past!");
                        throw new BadRequestException("Date due is in the past!");
                    }
                } else {
                    logger.error("Date send is in the past!");
                    throw new BadRequestException("Date send is in the past!");
                }

            } else {
                logger.error("The account with that id doesn't exist in Database!");
                throw new ResourceNotFoundException("The account with that id doesn't exist in Database!");
            }

        } else {
            logger.error("The job with that id doesn't exist in Database!");
            throw new ResourceNotFoundException("The job with that id doesn't exist in Database!");
        }

    }


    public void deleteEmailJob(Long id) {
        if (emailJobRepository.existsById(id)) {
            EmailJob emailJob = emailJobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
            logger.info("Email successfully deleted!");
            emailJobRepository.delete(emailJob);
        } else {
            logger.error("The job with that id doesn't exist in Database!");
            throw new ResourceNotFoundException("The job with that id doesn't exist in Database!");
        }
    }

    public void deleteAllEmailJobs() {
        if (!emailJobRepository.findAll().isEmpty()) {
            for (EmailJob emailJob : emailJobRepository.findAll()) {
                EmailJob job = emailJobRepository.findById(emailJob.getId()).orElseThrow(() -> new ResourceNotFoundException("The email job with that id doesn't exist in Database!"));
                emailJobRepository.delete(job);
            }
            enableScheduler();
            logger.info("All emails successfully deleted!");
        } else {
            logger.error("No email jobs in Database!");
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

    public void setJobActiveOrDeactive(Long id, IsActiveInsert isActiveInsert) {
        EmailJob emailJob = emailJobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Email job with id:" + id + " doesn't exist in Database!"));
        if (isActiveInsert.getActive() == null) {
            throw new IllegalArgumentException("Invalid argument: status must be 'true' or 'false'");
        }
        emailJob.setActive(isActiveInsert.getActive());

        if (isActiveInsert.getActive()) {
            enableScheduler();
        } else {
            disableScheduler();
        }
        emailJobRepository.save(emailJob);
    }


    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void sendEmail() throws Exception {
        if (!enable) return;

        logger.info("A minute has passed!");

        List<EmailJob> list = emailJobRepository.findEmailJobsForSendingNow(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        List<Account> administratorsList = accountRepository.findAllAdministrators();
        for (EmailJob emailJob : list) {
            String tempEmail=emailJob.getEmailTo();
            String[] array = emailJob.getEmailTo().split("\\s*,\\s*");
            if (emailJob.getRepetitive().name().equals("REPETITIVE")) {
                for (String s : array) {
                    emailJob.setEmailTo(s);
                    emailSenderService.sendMailOnTime(emailJob);
                }

                logger.info("Email send successfully to all emails!");

//            job.setDateDue(job.getDateDue().plusHours(job.getRepetition().getInHours()));
                emailJob.setEmailTo(tempEmail);
                emailJob.setNextSendTime(emailJob.getNextSendTime().plusMinutes(2));
            } else {
                for (String s : array) {
                    emailJob.setEmailTo(s);
                    emailSenderService.sendMailOnTime(emailJob);
                }

                logger.info("Email send successfully!");

                emailJob.setEmailTo(tempEmail);
                emailJob.setActive(false);
            }
            if (emailJob.getNextSendTime().isAfter(emailJob.getDateDue())) {
                emailJob.setActive(false);
            }
            if (emailJob.getMaxNumOfTrys().equals(emailJob.getNumOfFailedTrys())) {
                SimpleMailMessage message = new SimpleMailMessage();
                ExceptionEntity exception = exceptionEntityRepository.findTopById_jobOrderByDateOfExceptionDesc(emailJob.getId()).getFirst();
                if (exception.isSend()) {
                    for (Account allAdministrator : administratorsList) {
                        message.setSubject("Exception from user");
                        message.setText("Exception id: " + exception.getUuid() + "\n" +
                                "Exception message: " + exception.getMessage() + "\n" +
                                "Email job id from exception: " + exception.getId_job() + "\n" +
                                "Date of exception: " + exception.getDateOfException());
                        message.setFrom("bezbednostinformaciska@gmail.com");
                        message.setTo(allAdministrator.getEmail());

                        logger.info("Email send to administrator!");

                        mailSender.send(message);
                        emailJob.setNumOfFailedTrys(0);
                    }
                    exception.setSend(false);
                }
            }
        }
        emailJobRepository.saveAll(list);
        emailJobRepository.flush();
    }
}
package com.iwEmailSender.iwemailsender.Model;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Testing {
//    @Scheduled(fixedRate = 60000) // секоја минута
//    public void checkAndSendEmail() {
//        List<EmailJob> jobs = emailJobRepository.findPendingJobs();
//        LocalDateTime now = LocalDateTime.now();
//
//        for (EmailJob job : jobs) {
//            if (!now.isBefore(job.getDateDue()) &&
//                    now.isBefore(job.getDateDue().plusDays(1))) {
//
//                LocalTime currentTime = LocalTime.now();
//                if (!currentTime.isBefore(job.getTimeToSent()) &&
//                        currentTime.isBefore(job.getTimeToSent().plusMinutes(30))) {
//
//                    try {
//                        SimpleMailMessage message = new SimpleMailMessage();
//                        message.setSubject(job.getSubject());
//                        message.setText(job.getMessage());
//                        message.setFrom(job.getEmailFrom());
//                        message.setTo(job.getEmailTo());
//                        mailSender.send(message);
//                        job.setStatus(statusRepository.findById(1L).orElseThrow());
//
//                        job.setDateDue(job.getDateDue().plusHours(job.getRepetision().getInHours()));
//                    } catch (Exception e) {
//                        job.setNumOfFailedTrys(job.getNumOfFailedTrys() + 1);
//                        job.setStatus(statusRepository.findById(2L).orElseThrow());
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

}

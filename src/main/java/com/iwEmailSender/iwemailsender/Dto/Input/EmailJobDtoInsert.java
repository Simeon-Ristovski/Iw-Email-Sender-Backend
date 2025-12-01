package com.iwEmailSender.iwemailsender.Dto.Input;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmailJobDtoInsert {
    private String subject;
    private String message;
    private String emailTo;
    private LocalDateTime dateSend;
    private LocalDateTime dateDue;
    private LocalTime timeToSent;
    private String repetition;
    private String repetitive;
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getEmailTo() {
        return emailTo;
    }
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
    public LocalDateTime getDateSend() {
        return dateSend;
    }
    public void setDateSend(LocalDateTime dateSend) {
        this.dateSend = dateSend;
    }
    public LocalDateTime getDateDue() {
        return dateDue;
    }
    public void setDateDue(LocalDateTime dateDue) {
        this.dateDue = dateDue;
    }
    public LocalTime getTimeToSent() {
        return timeToSent;
    }
    public void setTimeToSent(LocalTime timeToSent) {
        this.timeToSent = timeToSent;
    }
    public String getRepetition() {
        return repetition;
    }
    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }
    public String getRepetitive() {
        return repetitive;
    }
    public void setRepetitive(String repetitive) {
        this.repetitive = repetitive;
    }
}
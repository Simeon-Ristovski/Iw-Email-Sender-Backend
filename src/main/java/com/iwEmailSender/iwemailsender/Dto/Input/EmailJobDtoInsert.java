package com.iwEmailSender.iwemailsender.Dto.Input;

import com.iwEmailSender.iwemailsender.Model.Repetision;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmailJobDtoInsert {

    private String subject;
    private String message;
    private String emailFrom;
    private String emailTo;
    private LocalDateTime dateSend;
    private LocalDateTime dateDue;
    private LocalTime timeToSent;
    private Repetision repetision;
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

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
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

    public Repetision getRepetision() {
        return repetision;
    }

    public void setRepetision(Repetision repetision) {
        this.repetision = repetision;
    }

    public String getRepetitive() {
        return repetitive;
    }

    public void setRepetitive(String repetitive) {
        this.repetitive = repetitive;
    }
}

package com.iwEmailSender.iwemailsender.Dto.Output;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class EmailJobDto {
    private UUID uuid;
    private String subject;
    private String message;
    private String emailFrom;
    private String emailTo;
    private String set_by;
    private LocalDateTime dateSend;
    private LocalDateTime dateDue;
    private LocalTime timeToSent;
    private String repetition;
    private String status;
    private String repetitive;
    private String state;
    private Integer maxNumOfTrys;
    private Boolean isActive;
    public Integer getMaxNumOfTrys() {
        return maxNumOfTrys;
    }
    public void setMaxNumOfTrys(Integer maxNumOfTrys) {
        this.maxNumOfTrys = maxNumOfTrys;
    }
    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
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
    public String getSet_by() {
        return set_by;
    }
    public void setSet_by(String set_by) {
        this.set_by = set_by;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRepetitive() {
        return repetitive;
    }
    public void setRepetitive(String repetitive) {
        this.repetitive = repetitive;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
package com.iwEmailSender.iwemailsender.Dto.Input;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmailJobTimeToSendNextInsertDto {
    private LocalDateTime dateSend;
    private LocalDateTime dateDue;
    private LocalTime timeToSent;

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
}

package com.iwEmailSender.iwemailsender.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "exception")
public class ExceptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "exception_seq_gen")
    @SequenceGenerator(name = "exception_seq_gen",sequenceName = "exception_seq",allocationSize = 5)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "id_job")
    private Long idJob;
    @Column(name = "message")
    private String message;
    @Column(name = "date_of_exception")
    private LocalDateTime dateOfException;
    @Column(name = "is_send")
    private boolean isSend;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public Long getIdJob() {
        return idJob;
    }
    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getDateOfException() {
        return dateOfException;
    }
    public void setDateOfException(LocalDateTime dateOfException) {
        this.dateOfException = dateOfException;
    }
    public boolean isSend() {
        return isSend;
    }
    public void setSend(boolean send) {
        isSend = send;
    }
}

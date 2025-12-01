package com.iwEmailSender.iwemailsender.Dto.Output;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExeceptionEntityDto {
    private UUID uuid;
    private Long idJob;
    private UUID jobUUUID;
    private String message;
    private LocalDateTime dateOfException;
    public ExeceptionEntityDto(UUID idUuid, Long id_job, String message, LocalDateTime dateOfException) {
        this.uuid = idUuid;
        this.idJob = id_job;
        this.message = message;
        this.dateOfException = dateOfException;
    }
    public UUID getJobUUUID() {
        return jobUUUID;
    }
    public void setJobUUUID(UUID jobUUUID) {
        this.jobUUUID = jobUUUID;
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
}
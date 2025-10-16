package com.iwEmailSender.iwemailsender.Dto.Output;



import java.time.LocalDateTime;
import java.util.UUID;

public class ExeceptionEntityDto {

    private UUID uuid;
    private Long id_job;
    private String message;
    private LocalDateTime dateOfException;

    public ExeceptionEntityDto(UUID idUuid, Long id_job, String message, LocalDateTime dateOfException) {
        this.uuid = idUuid;
        this.id_job = id_job;
        this.message = message;
        this.dateOfException = dateOfException;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId_job() {
        return id_job;
    }

    public void setId_job(Long id_job) {
        this.id_job = id_job;
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

package com.iwEmailSender.iwemailsender.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "email_job")
public class EmailJob {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "emailjob_seq_gen")
    @SequenceGenerator(name = "emailjob_seq_gen",sequenceName = "email_job_seq",allocationSize = 1)
    @Column(name = "id")
    private long id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "subject")
    private String subject;
    @Column(name = "message")
    private String message;
    @Column(name = "email_from")
    private String emailFrom;
    @Column(name = "email_to")
    private String emailTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account")
    private Account set_by;
    @Column(name = "date_send")
    private LocalDateTime dateSend;
    @Column(name = "date_due")
    private LocalDateTime dateDue;
    @Column(name = "time_to_sent")
    private LocalTime timeToSent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_repetition")
    private Repetition repetition;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status")
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(name = "repetitive")
    private Repetitive repetitive;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;
    @Column(name = "max_num_of_trys")
    private Integer maxNumOfTrys;
    @Column(name = "num_of_failed_trys")
    private Integer numOfFailedTrys;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "modify_at")
    private LocalDateTime modifyAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modify_by")
    private String modifyBy;
    @Column(name = "next_send_time")
    private LocalDateTime nextSendTime;
    @Column(name = "is_active")
    private Boolean isActive;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public Account getSet_by() {
        return set_by;
    }
    public void setSet_by(Account set_by) {
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
    public Repetition getRepetition() {
        return repetition;
    }
    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Repetitive getRepetitive() {
        return repetitive;
    }
    public void setRepetitive(Repetitive repetitive) {
        this.repetitive = repetitive;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public Integer getMaxNumOfTrys() {
        return maxNumOfTrys;
    }
    public void setMaxNumOfTrys(Integer maxNumOfTrys) {
        this.maxNumOfTrys = maxNumOfTrys;
    }
    public Integer getNumOfFailedTrys() {
        return numOfFailedTrys;
    }
    public void setNumOfFailedTrys(Integer numOfFailedTrys) {
        this.numOfFailedTrys = numOfFailedTrys;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getModifyAt() {
        return modifyAt;
    }
    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getModifyBy() {
        return modifyBy;
    }
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
    public LocalDateTime getNextSendTime() {
        return nextSendTime;
    }
    public void setNextSendTime(LocalDateTime nextSendTime) {
        this.nextSendTime = nextSendTime;
    }
    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
    @Override
    public String toString() {
        return "EmailJob{" +
                ", id=" + uuid +
                ", subject='" + subject + '\'' +
                ", emailFrom='" + emailFrom + '\'' +
                ", emailTo='" + emailTo + '\'' +
                ", set_by=" + set_by +
                ", dateSend=" + dateSend +
                ", dateDue=" + dateDue +
                ", repetition=" + repetition +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", modifyAt=" + modifyAt +
                ", createdBy='" + createdBy + '\'' +
                ", modifyBy='" + modifyBy + '\'' +
                '}';
    }
}

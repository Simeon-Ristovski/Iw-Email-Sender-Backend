package com.iwEmailSender.iwemailsender.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "repetition")
public class Repetition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "repetition_seq_gen")
    @SequenceGenerator(name = "repetition_seq_gen",sequenceName = "repetition_seq",allocationSize = 1)
    @Column(name = "id")
    private long id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "repetition_name")
    private String repetitionName;
    @Column(name = "in_hours")
    private Integer inHours;
    @OneToMany(mappedBy = "repetition",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EmailJob> listOfEmailJobs= new ArrayList<>();
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
    public String getRepetitionName() {
        return repetitionName;
    }
    public void setRepetitionName(String repetitionName) {
        this.repetitionName = repetitionName;
    }
    public Integer getInHours() {
        return inHours;
    }
    public void setInHours(Integer inHours) {
        this.inHours = inHours;
    }
    public List<EmailJob> getListOfEmailJobs() {
        return listOfEmailJobs;
    }
    public void setListOfEmailJobs(List<EmailJob> listOfEmailJobs) {
        this.listOfEmailJobs = listOfEmailJobs;
    }
    @Override
    public String toString() {
        return repetitionName ;
    }
}

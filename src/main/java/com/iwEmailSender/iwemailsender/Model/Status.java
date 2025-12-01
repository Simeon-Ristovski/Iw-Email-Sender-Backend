package com.iwEmailSender.iwemailsender.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "status_seq_gen")
    @SequenceGenerator(name = "status_seq_gen",sequenceName = "status_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "status_name")
    private String statusName;
    @OneToMany(mappedBy = "status",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EmailJob> listOfEmailJobs= new ArrayList<>();
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
    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    public List<EmailJob> getListOfEmailJobs() {
        return listOfEmailJobs;
    }
    public void setListOfEmailJobs(List<EmailJob> listOfEmailJobs) {
        this.listOfEmailJobs = listOfEmailJobs;
    }
    @Override
    public String toString() {
        return statusName ;
    }
}

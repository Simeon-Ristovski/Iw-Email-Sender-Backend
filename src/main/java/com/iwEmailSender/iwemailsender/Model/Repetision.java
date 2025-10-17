package com.iwEmailSender.iwemailsender.Model;

import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "repetision")
public class Repetision {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "repetision_seq_gen")
    @SequenceGenerator(name = "repetision_seq_gen",sequenceName = "repetision_seq",allocationSize = 1)
    @Column(name = "id")
    private long id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "repetision_name")
    private String repetisionName;
    @Column(name = "in_hours")
    private Integer inHours;


    @OneToMany(mappedBy = "repetision",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
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

    public String getRepetisionName() {
        return repetisionName;
    }

    public void setRepetisionName(String repetisionName) {
        this.repetisionName = repetisionName;
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
        return repetisionName ;
    }
}

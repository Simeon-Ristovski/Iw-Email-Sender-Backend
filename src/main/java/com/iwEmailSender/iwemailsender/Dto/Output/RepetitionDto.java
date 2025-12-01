package com.iwEmailSender.iwemailsender.Dto.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepetitionDto {
    private UUID uuid;
    private String repetitionName;
    private Integer inHours;
    public Integer getInHours() {
        return inHours;
    }
    public void setInHours(Integer inHours) {
        this.inHours = inHours;
    }
    private List<String> listOfEmailJobs = new ArrayList<>();
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
    public List<String> getListOfEmailJobs() {
        return listOfEmailJobs;
    }
    public void setListOfEmailJobs(List<String> listOfEmailJobs) {
        this.listOfEmailJobs = listOfEmailJobs;
    }
}
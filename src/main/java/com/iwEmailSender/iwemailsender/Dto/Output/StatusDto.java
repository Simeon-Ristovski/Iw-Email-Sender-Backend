package com.iwEmailSender.iwemailsender.Dto.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StatusDto {
    private UUID uuid;
    private String statusName;
    private List<String> listOfEmailJobs= new ArrayList<>();
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
    public List<String> getListOfEmailJobs() {
        return listOfEmailJobs;
    }
    public void setListOfEmailJobs(List<String> listOfEmailJobs) {
        this.listOfEmailJobs = listOfEmailJobs;
    }
}

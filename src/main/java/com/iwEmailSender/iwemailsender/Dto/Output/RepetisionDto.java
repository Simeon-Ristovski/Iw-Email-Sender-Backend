package com.iwEmailSender.iwemailsender.Dto.Output;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepetisionDto {

    private UUID uuid;

    private String repetisionName;

    private List<String> listOfEmailJobs = new ArrayList<>();

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

    public List<String> getListOfEmailJobs() {
        return listOfEmailJobs;
    }

    public void setListOfEmailJobs(List<String> listOfEmailJobs) {
        this.listOfEmailJobs = listOfEmailJobs;
    }
}

package com.iwEmailSender.iwemailsender.Dto.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDto {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private List<String> roles = new ArrayList<>();


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

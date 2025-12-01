package com.iwEmailSender.iwemailsender.Dto.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoleDto {
    private UUID uuid;
    private String roleName;
    private List<String> listOfAccounts = new ArrayList<>();
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public List<String> getListOfAccounts() {
        return listOfAccounts;
    }
    public void setListOfAccounts(List<String> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
    }
}
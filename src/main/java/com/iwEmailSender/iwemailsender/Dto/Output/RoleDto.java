package com.iwEmailSender.iwemailsender.Dto.Output;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoleDto {

    private UUID uuid;
    private String roleName;
    private List<String> list_of_accounts = new ArrayList<>();


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

    public List<String> getList_of_accounts() {
        return list_of_accounts;
    }

    public void setList_of_accounts(List<String> list_of_accounts) {
        this.list_of_accounts = list_of_accounts;
    }
}

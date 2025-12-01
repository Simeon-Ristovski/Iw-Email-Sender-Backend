package com.iwEmailSender.iwemailsender.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen",sequenceName = "role_seq",allocationSize = 1)
    @Column(name = "id")
    private long id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "role_name")
    private String roleName;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_roles",

            joinColumns =@JoinColumn(name = "id_role"),
            inverseJoinColumns  =@JoinColumn(name = "id_account")

    )
    private List<Account> listOfAccounts= new ArrayList<>();
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
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public List<Account> getListOfAccounts() {
        return listOfAccounts;
    }
    public void setListOfAccounts(List<Account> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
    }
    @Override
    public String toString() {
        return  roleName ;
    }
}

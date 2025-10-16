package com.iwEmailSender.iwemailsender.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_seq_gen")
    @SequenceGenerator(name = "account_seq_gen",sequenceName = "account_seq",allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToMany(mappedBy = "list_of_accounts",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private  List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "set_by",cascade = CascadeType.ALL,orphanRemoval = true)
    private  List<EmailJob> list_of_jobs = new ArrayList<>();

    @Column(name = "created_at")
    private  LocalDateTime CreatedAt;
    @Column(name = "modify_at")
    private  LocalDateTime ModifyAt;
    @Column(name = "created_by")
    private  String CreatedBy;
    @Column(name = "modify_by")
    private  String ModifyBy;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<EmailJob> getList_of_jobs() {
        return list_of_jobs;
    }

    public void setList_of_jobs(List<EmailJob> list_of_jobs) {
        this.list_of_jobs = list_of_jobs;
    }

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }

    public LocalDateTime getModifyAt() {
        return ModifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        ModifyAt = modifyAt;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getModifyBy() {
        return ModifyBy;
    }

    public void setModifyBy(String modifyBy) {
        ModifyBy = modifyBy;
    }

    @Override
    public String toString() {
        return "Account "+ '\'' +
                ", id=" + uuid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'';
    }
}

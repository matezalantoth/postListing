package org.springbootapp.models;

import org.springbootapp.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String password;
    private Integer id;
    private UserStatus status;
    private LocalDateTime statusChangedAt;
    private List<Group> groups;

        public User() {
        setStatus(UserStatus.VALID);
        this.groups = new ArrayList<>();
    }

    public boolean isUnderReview() {
        return status == UserStatus.UNDER_REVIEW;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

}

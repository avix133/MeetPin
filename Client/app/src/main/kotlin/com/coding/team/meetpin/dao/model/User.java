package com.coding.team.meetpin.dao.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;

    private String username;

    private String email;

    protected User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User {" +
                "id = " + id +
                ", username = " + username +
                ", email = " + email +
                "}";
    }
}

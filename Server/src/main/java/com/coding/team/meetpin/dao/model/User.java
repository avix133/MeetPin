package com.coding.team.meetpin.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String email;

    @OneToMany(mappedBy = "user")
    private Set<PinToFriend> pinToFriend;

    @OneToMany(mappedBy = "user")
    private Set<PinAnswer> pinAnswer;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Relationship> relationship;

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

    public Set<PinToFriend> getPinToFriend() {
        return pinToFriend;
    }

    public void setPinToFriend(Set<PinToFriend> pinToFriend) {
        this.pinToFriend = pinToFriend;
    }

    public Set<PinAnswer> getPinAnswer() {
        return pinAnswer;
    }

    public void setPinAnswer(Set<PinAnswer> pinAnswer) {
        this.pinAnswer = pinAnswer;
    }

    public Set<Relationship> getRelationship() {
        return relationship;
    }

    public void setRelationship(Set<Relationship> relationship) {
        this.relationship = relationship;
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

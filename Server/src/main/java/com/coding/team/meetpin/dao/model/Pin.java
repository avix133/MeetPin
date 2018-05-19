package com.coding.team.meetpin.dao.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pin")
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private User user;

    private String message;

    private Double map_latitude;

    private Double map_longitude;

    private Timestamp meeting_date;

    private Timestamp expire;

    private Set<PinAnswer> pinAnswers;

    private Set<PinToFriend> pinToFriends;

    private PinToGlobal pinToGlobal;

    public Pin(String message, User user, Double map_latitude, Double map_longitude, Timestamp meeting_date, Timestamp expire) {
        this.message = message;
        this.user = user;
        this.map_latitude = map_latitude;
        this.map_longitude = map_longitude;
        this.meeting_date = meeting_date;
        this.expire = expire;
        pinAnswers = new HashSet<>();
        pinToFriends = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getMap_latitude() {
        return map_latitude;
    }

    public void setMap_latitude(Double map_latitude) {
        this.map_latitude = map_latitude;
    }

    public Double getMap_longitude() {
        return map_longitude;
    }

    public void setMap_longitude(Double map_longitude) {
        this.map_longitude = map_longitude;
    }

    public Timestamp getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(Timestamp meeting_date) {
        this.meeting_date = meeting_date;
    }

    public Timestamp getExpire() {
        return expire;
    }


    public void setExpire(Timestamp expire) {
        this.expire = expire;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pin")
    public Set<PinAnswer> getPinAnswers() {
        return pinAnswers;
    }

    public void setPinAnswers(Set<PinAnswer> pinAnswers) {
        this.pinAnswers = pinAnswers;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pin")
    public Set<PinToFriend> getPinToFriends() {
        return pinToFriends;
    }

    public void setPinToFriends(Set<PinToFriend> pinToFriends) {
        this.pinToFriends = pinToFriends;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pin")
    public PinToGlobal getPinToGlobal() { return pinToGlobal; }

    public void setPinToGlobal(PinToGlobal pinToGlobal) {
        this.pinToGlobal = pinToGlobal;
    }

    @Override
    public String toString() {
        return "Pin{" +
                "id=" + id +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", map_latitude=" + map_latitude +
                ", map_longitude=" + map_longitude +
                ", meeting_date=" + meeting_date +
                ", expire=" + expire +
                ", pinAnswers=" + pinAnswers +
                ", pinToFriends=" + pinToFriends +
                ", pinToGlobal=" + pinToGlobal +
                '}';
    }
}

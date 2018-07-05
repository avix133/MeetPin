package com.coding.team.meetpin.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "pin")
public class Pin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String message;

    @Column(precision = 10, scale = 2)
    private Double map_latitude;

    @Column(precision = 11, scale = 3)
    private Double map_longitude;

    private Date meeting_date;

    private Date expire;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PinToGlobal pinToGlobal;

    protected Pin() {}

    public Pin(String message, User user, Double map_latitude, Double map_longitude, Date meeting_date, Date expire) {
        this.message = message;
        this.user = user;
        this.map_latitude = map_latitude;
        this.map_longitude = map_longitude;
        this.meeting_date = meeting_date;
        this.expire = expire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(Date meeting_date) {
        this.meeting_date = meeting_date;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public PinToGlobal getPinToGlobal() {
        return pinToGlobal;
    }

    public void setPinToGlobal(PinToGlobal pinToGlobal) {

        if (pinToGlobal == null) {
            if (this.pinToGlobal != null) {
                this.pinToGlobal.setPin_id(this.getId());
            }
        } else {
            pinToGlobal.setPin_id(this.getId());
        }
        this.pinToGlobal = pinToGlobal;
    }

    @Override
    public String toString() {
        return "Pin{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", message='" + message + '\'' +
                ", map_latitude=" + map_latitude +
                ", map_longitude=" + map_longitude +
                ", meeting_date=" + meeting_date +
                ", expire=" + expire +
                ", pinToGlobal=" + pinToGlobal +
                '}';
    }
}

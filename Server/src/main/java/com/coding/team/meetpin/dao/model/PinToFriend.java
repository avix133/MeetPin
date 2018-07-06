package com.coding.team.meetpin.dao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PinToFriend.class)
@Table(name = "pin_to_friend")
public class PinToFriend implements Serializable {


    @Id
    @Column(name = "to_user_id")
    private int userId;

    @Id
    @Column(name = "pin_id")
    private int pinId;

    public PinToFriend() {}

    public PinToFriend(int pinId, int userId) {
        this.pinId = pinId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPinId() {
        return pinId;
    }

    public void setPinId(int pin) {
        this.pinId = pin;
    }

    @Override
    public String toString() {
        return "PinToFriend{" +
                "userId=" + userId +
                ", pin=" + pinId +
                '}';
    }
}

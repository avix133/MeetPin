package com.coding.team.meetpin.dao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PinToFriend.class)
@Table(name = "pin_to_friend")
public class PinToFriend implements Serializable {

    private User user;

    private Pin pin;

    protected PinToFriend() {}

    public PinToFriend(Pin pin, User user) {
        this.pin = pin;
        this.user = user;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "to_user_id")
    public User  getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "pin_id")
    public Pin getPin() {
        return pin;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "PinToFriend{" +
                "user=" + user +
                ", pin=" + pin +
                '}';
    }
}

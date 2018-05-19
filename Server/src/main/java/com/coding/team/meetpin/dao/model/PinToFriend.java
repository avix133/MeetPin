package com.coding.team.meetpin.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "pin_to_friend")
public class PinToFriend {

    private User user;

    private Pin pin;

    @Id
    @OneToMany
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @OneToMany
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

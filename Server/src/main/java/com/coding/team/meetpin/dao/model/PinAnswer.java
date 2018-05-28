package com.coding.team.meetpin.dao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PinAnswer.class)
@Table(name = "pin_answer")
public class PinAnswer implements Serializable {

    private Pin pin;

    private User user;

    private boolean answer;

    public PinAnswer() {}

    public PinAnswer(Pin pin, User user, boolean answer) {
        this.pin = pin;
        this.user = user;
        this.answer = answer;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "pin_id")
    public Pin getPin() {
        return pin;
    }

    public void setPin(Pin pin_id) {
        this.pin = pin_id;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user_id) {
        this.user = user_id;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Pin Answer {" +
                "pin = " + pin +
                ", user = " + user +
                ", answer = " + answer +
                "}";
    }
}

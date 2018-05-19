package com.coding.team.meetpin.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "pin_answer")
public class PinAnswer {

    private Pin pin_id;

    private User user_id;

    private boolean answer;

    @Id
    @OneToOne
    @JoinColumn(name = "pin_id")
    public Pin getPin() {
        return pin_id;
    }

    public void setPin(Pin pin_id) {
        this.pin_id = pin_id;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user_id;
    }

    public void setUser(User user_id) {
        this.user_id = user_id;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public String toString() {
        return "Pin Answer {" +
                "pin_id = " + pin_id +
                ", user_id = " + user_id +
                ", answer = " + answer +
                "}";
    }
}

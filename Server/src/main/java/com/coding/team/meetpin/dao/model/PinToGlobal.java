package com.coding.team.meetpin.dao.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pin_to_global")
public class PinToGlobal implements Serializable {


    @Id
    @Column(name = "pin_id")
    private int pin_id;

    public PinToGlobal(){}

    public PinToGlobal(int pin_id) {
        this.pin_id = pin_id;
    }

    public int getPin_id() { return pin_id;}

    public void setPin_id(int pin_id) { this.pin_id = pin_id;}

    @Override
    public String toString() {
        return "PinToGlobal{" +
                "pin_id=" + pin_id +
                '}';
    }
}

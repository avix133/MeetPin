package com.coding.team.meetpin.dao.model;


import java.io.Serializable;

public class PinToGlobal implements Serializable {

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

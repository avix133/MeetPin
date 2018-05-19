package com.coding.team.meetpin.dao.model;


import javax.persistence.*;

@Entity
@Table(name = "pin_to_global")
public class PinToGlobal {

    private Pin pin_id;

    public PinToGlobal(Pin pin_id) {
        this.pin_id = pin_id;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "pin_id")
    public Pin getPin_id() { return pin_id;}

    public void setPin_id(Pin pin_id) { this.pin_id = pin_id;}
}

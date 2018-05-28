package com.coding.team.meetpin.dao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "relationship")
public class Relationship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_one_id")
    private int user_one;

    @Column(name = "user_two_id")
    private int user_two;

    @Column(name = "action_user_id")
    private int action_user;

    private boolean status;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_one_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "user_two_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "action_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private User user;

    public Relationship() {}

    public Relationship(int user_one, int user_two, int action_user, boolean status) {
        this.user_one = user_one;
        this.user_two = user_two;
        this.action_user = action_user;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_one() {
        return user_one;
    }

    public void setUser_one(int user_one) {
        this.user_one = user_one;
    }

    public int getUser_two() {
        return user_two;
    }

    public void setUser_two(int user_two) {
        this.user_two = user_two;
    }

    public int getAction_user() {
        return action_user;
    }

    public void setAction_user(int action_user) {
        this.action_user = action_user;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", user_one=" + user_one +
                ", user_two=" + user_two +
                ", action_user=" + action_user +
                ", status=" + status +
                '}';
    }
}

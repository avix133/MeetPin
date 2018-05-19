package com.coding.team.meetpin.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "relationship")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private User user_one;

    private User user_two;

    private User action_user;

    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "user_one_id")
    public User getUser_one() {
        return user_one;
    }

    public void setUser_one(User user_one) {
        this.user_one = user_one;
    }

    @OneToOne
    @JoinColumn(name = "user_two_id")
    public User getUser_two() {
        return user_two;
    }

    public void setUser_two(User user_two) {
        this.user_two = user_two;
    }

    @OneToOne
    @JoinColumn(name = "action_user_id")
    public User getAction_user() {
        return action_user;
    }

    public void setAction_user(User action_user) {
        this.action_user = action_user;
    }

    public boolean isStatus() {
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

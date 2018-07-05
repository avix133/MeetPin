package com.coding.team.meetpin.client_server.request.impl;

import java.util.List;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.dao.model.Pin;
import com.coding.team.meetpin.dao.model.User;

public class AddPinRequest implements Request {

    private static final long serialVersionUID = -6292359922357076064L;
    private int clientId;
    private Pin pin;
    private boolean global;
    private List<User> recipients;

    public AddPinRequest(final int clientId, Pin pin, boolean global, List<User> recipients) {
        this.clientId = clientId;
        this.pin = pin;
        this.global = global;
        this.recipients = recipients;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.ADD_PIN;
    }

    public Pin getPin() {
        return pin;
    }

    public boolean isGlobal() {
        return global;
    }

    public List<User> getRecipients() {
        return recipients;
    }
}

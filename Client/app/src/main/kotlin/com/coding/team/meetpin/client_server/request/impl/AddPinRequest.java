package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.dao.model.Pin;

public class AddPinRequest implements Request {

    private static final long serialVersionUID = -6292359922357076064L;
    private int clientId;
    private Pin pin;

    public AddPinRequest(final int clientId, Pin pin) {
        this.clientId = clientId;
        this.pin = pin;
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
}

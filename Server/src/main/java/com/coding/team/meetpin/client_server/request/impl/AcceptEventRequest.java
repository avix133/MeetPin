package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.dao.model.Pin;

public class AcceptEventRequest implements Request {

    private static final long serialVersionUID = -6292359922357076064L;
    private int clientId;
    private int pinId;

    public AcceptEventRequest(final int clientId, int pinId) {
        this.clientId = clientId;
        this.pinId = pinId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.ADD_PIN;
    }

    public int getPinId() {
        return pinId;
    }
}

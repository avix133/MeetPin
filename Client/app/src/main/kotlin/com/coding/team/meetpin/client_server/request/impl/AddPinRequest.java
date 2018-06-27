package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class AddPinRequest implements Request {

    private int clientId;

    public AddPinRequest(final int clientId) {
        this.clientId = clientId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.ADD_PIN;
    }
}

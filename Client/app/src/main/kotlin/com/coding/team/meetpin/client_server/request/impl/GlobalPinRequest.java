package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class GlobalPinRequest implements Request {

    private static final long serialVersionUID = 8122735772485988104L;
    private int clientId;

    public GlobalPinRequest(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.GLOBAL_PINS;
    }
}
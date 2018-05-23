package com.coding.team.meetpin.client_server.request.impl;


import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class PinDataRequest implements Request {

    private static final long serialVersionUID = -7980643157731081443L;

    private int clientId;
    private int pinId;

    public PinDataRequest(final int clientId, final int pinId) {
        this.clientId = clientId;
        this.pinId = pinId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.PIN_DATA;
    }

    public int getPinId() {
        return pinId;
    }
}

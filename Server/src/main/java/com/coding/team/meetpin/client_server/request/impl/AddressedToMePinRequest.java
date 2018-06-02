package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class AddressedToMePinRequest implements Request {
    private static final long serialVersionUID = 7726095073783947094L;

    private int clientId;
    private int pinId;

    public AddressedToMePinRequest(int clientId, final int pinId) {
        this.clientId = clientId;
        this.pinId = pinId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.ADDRESSED_TO_ME_PINS;
    }

    public int getPinId() {
        return pinId;
    }
}

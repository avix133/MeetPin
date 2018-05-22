package com.coding.team.meetpin.client_server.communication.requests;


import java.io.Serializable;

import com.coding.team.meetpin.client_server.communication.Request;
import com.coding.team.meetpin.client_server.communication.RequestType;

public class PinDataRequest implements Request, Serializable {

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

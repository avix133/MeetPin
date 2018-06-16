package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class DisplayPinRequest implements Request {

    private static final long serialVersionUID = -1839392736179779349L;
    private int clientId;

    public DisplayPinRequest(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.DISPLAY_PINS;
    }
}

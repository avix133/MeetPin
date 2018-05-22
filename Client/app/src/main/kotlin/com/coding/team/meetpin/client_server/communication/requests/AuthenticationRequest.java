package com.coding.team.meetpin.client_server.communication.requests;

import com.coding.team.meetpin.client_server.communication.Request;
import com.coding.team.meetpin.client_server.communication.RequestType;

public class AuthenticationRequest implements Request {

    private static final RequestType REQUEST_TYPE = RequestType.AUTHENTICATE;
    private String email;
    private int clientId = -1;

    public AuthenticationRequest(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return REQUEST_TYPE;
    }
}

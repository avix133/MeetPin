package com.coding.team.meetpin.client_server.request.impl;


import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class AuthenticationRequest implements Request {

    private static final long serialVersionUID = 988094335343464151L;
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

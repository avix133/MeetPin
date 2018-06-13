package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class InviteFriendRequest implements Request {

    private static final long serialVersionUID = -4511945576735207817L;
    private int clientId;
    private String email;

    public InviteFriendRequest(final int clientId, final String email) {
        this.clientId = clientId;
        this.email = email;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.INVITE_FRIEND;
    }

    public String getUserEmail() {
        return email;
    }
}

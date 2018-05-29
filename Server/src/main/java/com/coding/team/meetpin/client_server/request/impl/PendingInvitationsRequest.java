package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class PendingInvitationsRequest implements Request {

    private static final long serialVersionUID = 4044071932928272122L;
    private int clientId;

    public PendingInvitationsRequest(final int clientId) {
        this.clientId = clientId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.PENDING_INVITATIONS;
    }
}

package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class RemoveFriendRequest implements Request {

    private static final long serialVersionUID = 239748610650618403L;
    private int clientId;
    private int userId;

    public RemoveFriendRequest(final int clientId, final int userId) {
        this.clientId = clientId;
        this.userId = userId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.REMOVE_FRIEND;
    }

    public int getUserId() {
        return userId;
    }
}

package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class FriendListRequest implements Request {

    private static final long serialVersionUID = 4114712813693071740L;
    private int clientId;

    public FriendListRequest(final int clientId) {
        this.clientId = clientId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.FRIEND_LIST;
    }
}

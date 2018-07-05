package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;

public class AcceptFriendRequest implements Request {

    private static final long serialVersionUID = -3811428572176557610L;
    private int clientId;
    private String username;

    public AcceptFriendRequest(int clientId, String username) {
        this.clientId = clientId;
        this.username = username;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public RequestType getType() {
        return RequestType.ACCEPT_FRIEND_REQUEST;
    }

    public String getUsername() {
        return username;
    }
}

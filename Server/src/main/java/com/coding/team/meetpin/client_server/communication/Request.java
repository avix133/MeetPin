package com.coding.team.meetpin.client_server.communication;

public interface Request {
    int getClientId();
    RequestType getType();
}

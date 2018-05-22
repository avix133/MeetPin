package com.coding.team.meetpin.client_server.communication;

import java.io.Serializable;

public class Response implements Serializable {
    private RequestType type;
    private Object payload;

    public Response(RequestType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public RequestType getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }
}

package com.coding.team.meetpin.client_server.response.impl;

import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.client_server.response.Response;

public class DefaultResponse implements Response {
    private static final long serialVersionUID = 8441422050300824367L;

    private RequestType type;
    private Object payload;

    public DefaultResponse(RequestType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    @Override
    public RequestType getType() {
        return type;
    }

    @Override
    public Object getPayload() {
        return payload;
    }
}

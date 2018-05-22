package com.coding.team.meetpin.client.communication;

import java.io.Serializable;

public class NettyMessage implements Serializable {
    private MessageType type;
    private Object payload;

    public NettyMessage(MessageType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public MessageType getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }
}

package com.coding.team.meetpin.client_server.communication;

import java.io.Serializable;

public interface Request extends Serializable {
    int getClientId();
    RequestType getType();
}

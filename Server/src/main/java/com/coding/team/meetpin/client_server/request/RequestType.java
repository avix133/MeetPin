package com.coding.team.meetpin.client_server.request;

import java.io.Serializable;

public enum RequestType implements Serializable {
    AUTHENTICATE, PIN_DATA, ADD_PIN, GLOBAL_PINS
}

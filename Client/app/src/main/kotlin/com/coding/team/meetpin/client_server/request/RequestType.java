package com.coding.team.meetpin.client_server.request;

import java.io.Serializable;

public enum RequestType implements Serializable {
    AUTHENTICATE,
    PIN_DATA,
    ADD_PIN,
    GLOBAL_PINS,
    DISPLAY_PINS,
    ADDRESSED_TO_ME_PINS,
    FRIEND_LIST,
    PENDING_INVITATIONS,
    INVITE_FRIEND,
    REMOVE_FRIEND,
    ACCEPT_EVENT,
    ACCEPT_FRIEND_REQUEST
}

package com.coding.team.meetpin.client_server;

import com.coding.team.meetpin.client_server.response.impl.DefaultResponse;
import java.util.concurrent.Future;

public interface MeetPinService {
    Future<DefaultResponse> getPinData(int pinId);
    Future<DefaultResponse> getGlobalPins();
    boolean authenticate(String email);
}

package com.coding.team.meetpin.client_server;

import com.coding.team.meetpin.client_server.communication.Response;
import io.netty.util.concurrent.Future;

public interface MeetPinService {
    Future<Response> getPinData(int pinId);
    boolean authenticate(String email);
}

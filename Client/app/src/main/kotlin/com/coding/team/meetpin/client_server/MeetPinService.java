package com.coding.team.meetpin.client_server;

import com.coding.team.meetpin.client_server.response.impl.DefaultResponse;
import io.netty.util.concurrent.Future;

public interface MeetPinService {
    Future<DefaultResponse> getPinData(int pinId);
    boolean authenticate(String email);
}

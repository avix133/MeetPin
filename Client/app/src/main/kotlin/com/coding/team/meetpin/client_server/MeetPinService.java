package com.coding.team.meetpin.client_server;

import com.coding.team.meetpin.client_server.response.Response;
import com.coding.team.meetpin.client_server.response.impl.DefaultResponse;
import java.util.concurrent.Future;

public interface MeetPinService {
    Future<Response> getPinData(int pinId);
    Future<Response> getGlobalPins();
    Future<Response> getPinsAddressedToMe(int userId);
    boolean authenticate(String email);

    Future<Response> getFriendList();
    Future<Response> getPendingInvitations();
    Future<Response> inviteFriend(int friendId);
    Future<Response> removeFriend(int friendId);

}
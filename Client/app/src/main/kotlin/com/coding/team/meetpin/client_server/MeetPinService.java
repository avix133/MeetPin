package com.coding.team.meetpin.client_server;

import java.util.concurrent.Future;

import com.coding.team.meetpin.client_server.response.Response;

public interface MeetPinService {
    Future<Response> getPinData(int pinId);
    Future<Response> getGlobalPins();
    Future<Response> getDisplayPins();
    Future<Response> getPinsAddressedToMe(int userId);
    boolean authenticate(String email);

    Future<Response> getFriendList();
    Future<Response> getPendingInvitations();
    Future<Response> inviteFriend(String email);
    Future<Response> removeFriend(int friendId);
    Future<Response> addPin();
}
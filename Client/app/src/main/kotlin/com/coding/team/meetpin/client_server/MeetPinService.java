package com.coding.team.meetpin.client_server;

import com.coding.team.meetpin.client_server.response.impl.DefaultResponse;
import java.util.concurrent.Future;

public interface MeetPinService {
    Future<DefaultResponse> getPinData(int pinId);
    Future<DefaultResponse> getGlobalPins();
    Future<DefaultResponse> getPinsAddressedToMe(int userId);
    boolean authenticate(String email);

    Future<DefaultResponse> getFriendList();
    Future<DefaultResponse> getPendingInvitations();
    Future<DefaultResponse> inviteFriend(int friendId);
    Future<DefaultResponse> removeFriend(int friendId);

}
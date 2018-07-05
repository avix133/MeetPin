package com.coding.team.meetpin.client_server;

import java.util.List;
import java.util.concurrent.Future;

import com.coding.team.meetpin.client_server.response.Response;
import com.coding.team.meetpin.dao.model.Pin;
import com.coding.team.meetpin.dao.model.User;

public interface MeetPinService {
    boolean authenticate(String email);
    Future<Response> acceptEvent(int pinId);
    Future<Response> getPinData(int pinId);
    Future<Response> getGlobalPins();
    Future<Response> getDisplayPins();
    Future<Response> getPinsAddressedToMe(int userId);
    Future<Response> getFriendList();
    Future<Response> getPendingInvitations();
    Future<Response> inviteFriend(String email);
    Future<Response> removeFriend(int friendId);
    Future<Response> addPin(Pin pin, boolean global, List<User> recipients);
    Future<Response> acceptFriendRequest(String username);
}
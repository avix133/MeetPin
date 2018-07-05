package com.coding.team.meetpin.client_server.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.coding.team.meetpin.client_server.MeetPinService;
import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.client_server.request.impl.AcceptEventRequest;
import com.coding.team.meetpin.client_server.request.impl.AddPinRequest;
import com.coding.team.meetpin.client_server.request.impl.AddressedToMePinRequest;
import com.coding.team.meetpin.client_server.request.impl.AuthenticationRequest;
import com.coding.team.meetpin.client_server.request.impl.DisplayPinRequest;
import com.coding.team.meetpin.client_server.request.impl.FriendListRequest;
import com.coding.team.meetpin.client_server.request.impl.GlobalPinRequest;
import com.coding.team.meetpin.client_server.request.impl.InviteFriendRequest;
import com.coding.team.meetpin.client_server.request.impl.PendingInvitationsRequest;
import com.coding.team.meetpin.client_server.request.impl.PinDataRequest;
import com.coding.team.meetpin.client_server.request.impl.RemoveFriendRequest;
import com.coding.team.meetpin.client_server.response.Response;
import com.coding.team.meetpin.dao.model.Pin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;


public class ClientHandler extends ChannelInboundHandlerAdapter implements MeetPinService {

    private ChannelHandlerContext ctx;
    private static ClientHandler instance = null;
    public int clientId = -1;
    private ConcurrentHashMap<RequestType, Promise<Response>> messageMap = new ConcurrentHashMap<>();


    private ClientHandler() {
    }

    public static ClientHandler getInstance() {
        if (instance == null) {
            instance = new ClientHandler();
        }

        return instance;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        instance.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Client read: " + msg);
        Response response = (Response) msg;
        synchronized (this) {
            if (messageMap != null) {
                messageMap.get(response.getType()).setSuccess(response);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        instance.ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

    private Future<Response> sendRequest(Request request) {
        Promise<Response> promise = GlobalEventExecutor.INSTANCE.newPromise();
        synchronized (this) {
            if (messageMap == null) {
                promise.setFailure(new IllegalStateException());
            } else {
                messageMap.put(request.getType(), promise);
                try {
                    instance.ctx.writeAndFlush(request);
                }
                catch (Exception e) {
                    return null;
                }
            }
        }
        return promise;
    }

    @Override
    public boolean authenticate(String email) {
        Request request = new AuthenticationRequest(email);
        Future<Response> future = sendRequest(request);
        try {
            assert future != null;
            Response response = future.get(10, TimeUnit.SECONDS);
            clientId = (int) response.getPayload();
        } catch (InterruptedException | NullPointerException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return this.clientId > -1;
    }

    @Override
    public Future<Response> acceptEvent(final int pinId) {
        Request request = new AcceptEventRequest(clientId, pinId);
        return sendRequest(request);
    }

    @Override
    public Future<Response> getPinData(int pinId) {
        Request request = new PinDataRequest(clientId, pinId);
        return sendRequest(request);
    }

    @Override
    public Future<Response> getGlobalPins() {
        Request request = new GlobalPinRequest(clientId);
        return sendRequest(request);
    }

    @Override
    public Future<Response> getPinsAddressedToMe(int userId) {
        Request request = new AddressedToMePinRequest(clientId);
        return sendRequest(request);
    }


    @Override
    public Future<Response> getFriendList() {
        Request request = new FriendListRequest(clientId);
        return sendRequest(request);
    }

    @Override
    public Future<Response> getPendingInvitations() {
        Request request = new PendingInvitationsRequest(clientId);
        return sendRequest(request);
    }

    @Override
    public Future<Response> inviteFriend(String email) {
        Request request = new InviteFriendRequest(clientId, email);
        return sendRequest(request);
    }

    @Override
    public Future<Response> removeFriend(int friend_id) {
        Request request = new RemoveFriendRequest(clientId, friend_id);
        return sendRequest(request);
    }

    @Override
    public Future<Response> addPin(Pin pin) {
        Request request = new AddPinRequest(clientId, pin);
        return sendRequest(request);
    }

    @Override
    public Future<Response> getDisplayPins() {
        Request request = new DisplayPinRequest(clientId);
        return sendRequest(request);
    }

    public void sendMessage(Object msg) {
        instance.ctx.writeAndFlush(msg);
    }
}

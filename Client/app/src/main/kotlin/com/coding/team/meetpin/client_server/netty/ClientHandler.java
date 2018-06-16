package com.coding.team.meetpin.client_server.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.coding.team.meetpin.client_server.MeetPinService;
import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.client_server.request.impl.AddPinRequest;
import com.coding.team.meetpin.client_server.request.impl.AddressedToMePinRequest;
import com.coding.team.meetpin.client_server.request.impl.DisplayPinRequest;
import com.coding.team.meetpin.client_server.request.impl.FriendListRequest;
import com.coding.team.meetpin.client_server.request.impl.GlobalPinRequest;
import com.coding.team.meetpin.client_server.request.impl.InviteFriendRequest;
import com.coding.team.meetpin.client_server.request.impl.PendingInvitationsRequest;
import com.coding.team.meetpin.client_server.request.impl.RemoveFriendRequest;
import com.coding.team.meetpin.client_server.response.impl.DefaultResponse;
import com.coding.team.meetpin.client_server.request.impl.AuthenticationRequest;
import com.coding.team.meetpin.client_server.request.impl.PinDataRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;


public class ClientHandler extends ChannelInboundHandlerAdapter implements MeetPinService {

    private ChannelHandlerContext ctx;
    private static ClientHandler instance = null;
    private int clientId = -1;
    private ConcurrentHashMap<RequestType, Promise<DefaultResponse>> messageMap = new ConcurrentHashMap<>();


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
        DefaultResponse response = (DefaultResponse) msg;
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
        instance.ctx.close();
    }

    private Future<DefaultResponse> sendRequest(Request request) {
        Promise<DefaultResponse> promise = GlobalEventExecutor.INSTANCE.newPromise();
        synchronized (this) {
            if (messageMap == null) {
                promise.setFailure(new IllegalStateException());
            } else {
                messageMap.put(request.getType(), promise);
                instance.ctx.writeAndFlush(request);
            }
        }
        return promise;
    }

    @Override
    public boolean authenticate(String email) {
        Request request = new AuthenticationRequest(email);
        Future<DefaultResponse> future = sendRequest(request);
        try {
            DefaultResponse response = future.get(10, TimeUnit.SECONDS);
            clientId = (int) response.getPayload();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return clientId > -1;
    }

    @Override
    public Future<DefaultResponse> getPinData(int pinId) {
        Request request = new PinDataRequest(clientId, pinId);

        return sendRequest(request);
    }

    @Override
    public Future<DefaultResponse> getGlobalPins() {
        Request request = new GlobalPinRequest(clientId);

        return sendRequest(request);
    }

    @Override
    public Future<DefaultResponse> getDisplayPins() {
        Request request = new DisplayPinRequest(4);

        return sendRequest(request);
    }

    @Override
    public Future<DefaultResponse> getPinsAddressedToMe(int userId) {
        Request request = new AddressedToMePinRequest(clientId, userId);

        return sendRequest(request);
    }

    // from this point on clientId is hardcoded (to be changed later)
    @Override
    public Future<DefaultResponse> getFriendList() {
        Request request = new FriendListRequest(1);

        return sendRequest(request);
    }

    @Override
    public Future<DefaultResponse> getPendingInvitations() {
        Request request = new PendingInvitationsRequest(4);

        return sendRequest(request);
    }

    @Override
    public Future<DefaultResponse> inviteFriend(String email) {
        Request request = new InviteFriendRequest(1, email);

        return sendRequest(request);
    }

    @Override
    public Future<DefaultResponse> removeFriend(int friend_id) {
        Request request = new RemoveFriendRequest(1, friend_id);

        return  sendRequest(request);
    }

    @Override
    public Future<DefaultResponse> addPin() {
        Request request = new AddPinRequest(1);

        return sendRequest(request);
    }

    public void sendMessage(Object msg) {
        instance.ctx.writeAndFlush(msg);
    }
}

package com.coding.team.meetpin.client_server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.coding.team.meetpin.client_server.communication.Request;
import com.coding.team.meetpin.client_server.communication.RequestType;
import com.coding.team.meetpin.client_server.communication.Response;
import com.coding.team.meetpin.client_server.communication.requests.AuthenticationRequest;
import com.coding.team.meetpin.client_server.communication.requests.PinDataRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;


public class ClientHandler extends ChannelInboundHandlerAdapter implements MeetPinService {

    private ChannelHandlerContext ctx;
    private static ClientHandler instance = null;
    private int clientId = -1;
    private ConcurrentHashMap<RequestType, Promise<Response>> messageMap = new ConcurrentHashMap<>(16);


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
        instance.ctx.close();
    }

    private Future<Response> sendRequest(Request request) {
        Promise<Response> promise = GlobalEventExecutor.INSTANCE.newPromise();
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
        Future<Response> future = sendRequest(request);
        try {
            Response response = future.get(10, TimeUnit.SECONDS);
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
    public Future<Response> getPinData(int pinId) {
        Request request = new PinDataRequest(clientId, pinId);

        return sendRequest(request);
    }

    public void sendMessage(Object msg) {
        instance.ctx.writeAndFlush(msg);
    }
}

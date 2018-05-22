package com.coding.team.meetpin.client;

import com.coding.team.meetpin.client.communication.MessageType;
import com.coding.team.meetpin.client.communication.NettyMessage;

import java.nio.BufferOverflowException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;


public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx;
    private static NettyClientHandler instance = null;
    private BlockingQueue<Promise<NettyMessage>> messageList = new ArrayBlockingQueue<>(16);
//    private ConcurrentHashMap<MessageType, Promise<NettyMessage>> messageMap = new ConcurrentHashMap<>(16);


    private NettyClientHandler() { }

    public static NettyClientHandler getInstance() {
        if (instance == null) {
            instance = new NettyClientHandler();
        }
        System.out.println("Singleton returns: " + System.identityHashCode(instance));
        return instance;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        instance.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Client read: " + msg);
        synchronized(this){
            if(messageList != null) {
                messageList.poll().setSuccess((NettyMessage)msg);
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

    private Future<NettyMessage> sendMessage(Object msg, Promise<NettyMessage> prom) {
        synchronized (this) {
            if (messageList == null) {
                // Connection closed
                prom.setFailure(new IllegalStateException());
            } else if (messageList.offer(prom)) {
                // Connection open and message accepted
                instance.ctx.writeAndFlush(msg);
            } else {
                // Connection open and message rejected
                prom.setFailure(new BufferOverflowException());
            }

            return prom;
        }
    }

    public Future authenticate(Object msg) {
        return null;
    }

    public Future<NettyMessage> getSomething(Object msg) {
        return sendMessage(msg, GlobalEventExecutor.INSTANCE.<NettyMessage>newPromise());
    }
}

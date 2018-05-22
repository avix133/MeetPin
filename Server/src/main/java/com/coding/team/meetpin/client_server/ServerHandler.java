package com.coding.team.meetpin.client_server;

import com.coding.team.meetpin.client_server.communication.Request;
import com.coding.team.meetpin.client_server.communication.Response;
import com.coding.team.meetpin.client_server.communication.requests.PinDataRequest;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LogManager.getLogger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Request request = (Request)msg;
        logger.info("Received msg: " + ((Request) msg).getType());
        logger.info("Received msg: " + ((PinDataRequest) msg).getPinId());
        logger.info("Ctx: " + ctx.channel());
        new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(Request.class.getClassLoader()));

        sendResponse(ctx, new Response(request.getType(), "PAYLOAD FROM SERVER - " + ((PinDataRequest)request).getPinId()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void sendResponse(ChannelHandlerContext ctx, Response response) {
        ctx.write(response);
    }
}
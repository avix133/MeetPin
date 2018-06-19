package com.coding.team.meetpin.client_server.netty;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestResolver;
import com.coding.team.meetpin.client_server.response.Response;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LogManager.getLogger();
    private RequestResolver requestResolver;

    public ServerHandler(RequestResolver requestResolver) {
        this.requestResolver = requestResolver;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("Received msg type: " + ((Request) msg).getType());

        Response response = requestResolver.resolve((Request) msg);
        logger.info("Sending response: " + response.toString());
        sendResponse(ctx, response);

//        sendResponse(ctx, new DefaultResponse(request.getType(), "PAYLOAD FROM SERVER - " + ((PinDataRequest)request).getPinId()));
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
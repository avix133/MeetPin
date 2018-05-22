package com.coding.team.meetpin.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LogManager.getLogger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the client.
        logger.info("Received msg: " + msg);
        logger.info("Ctx: " + ctx.channel());
        String ret = msg + "srv";
        
        ctx.write(ret);
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
}
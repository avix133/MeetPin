package com.coding.team.meetpin;

import com.coding.team.meetpin.client_server.netty.ServerHandler;
import com.coding.team.meetpin.client_server.request.RequestResolver;
import com.coding.team.meetpin.client_server.request.impl.DefaultRequestResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.coding.team.meetpin.client_server.netty.Server;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args)
    {
        logger.info("Elo wariaty");

        RequestResolver requestResolver = new DefaultRequestResolver();

        ServerHandler serverHandler = new ServerHandler(requestResolver);
        Server server = new Server(8080, serverHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

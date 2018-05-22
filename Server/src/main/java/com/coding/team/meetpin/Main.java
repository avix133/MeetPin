package com.coding.team.meetpin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.coding.team.meetpin.client_server.NettyServer;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args)
    {
        logger.info("Elo wariaty");

//        MultiThreadedServer server = new MultiThreadedServer(8080);
        NettyServer server = new NettyServer(8080);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

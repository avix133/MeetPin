package com.coding.team.meetpin;

import com.coding.team.meetpin.server.MultiThreadedServer;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Server: Elo wariaty!");

        MultiThreadedServer server = new MultiThreadedServer(8080);
        server.run();
    }
}

package com.coding.team.meetpin.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Description of class:
 * <p>
 * Created on 17 Apr 2018 (20:55)
 *
 * @author dawid
 */

class ClientWorker extends Thread {
    private Socket clientSocket;

    ClientWorker(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            System.out.println("From client: " + input.toString());
            output.write((time + ": Hello!").getBytes());
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}

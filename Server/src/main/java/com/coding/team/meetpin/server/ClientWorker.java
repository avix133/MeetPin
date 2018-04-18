package com.coding.team.meetpin.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * Description of class:
 * <p>
 * Created on 17 Apr 2018 (20:55)
 *
 * @author dawid
 */

class ClientWorker extends Thread {
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    ClientWorker(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Date date = new Date();

            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.flush();

            inputStream = new ObjectInputStream(clientSocket.getInputStream());

            sendMessage("Connected!");

            try {
                System.out.println("Reading...");
                String message = (String) inputStream.readObject();
                System.out.println("Client:" + message);
            } catch (ClassNotFoundException e) {
                System.err.println("Data received in unknown format");
            }

            sendMessage("Hello " + date.toString());

            System.out.println("Request processed: " + clientSocket.getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(String msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            System.out.println("Im server! " + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}

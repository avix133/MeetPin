package com.coding.team.meetpin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by dawid on 17.04.18.
 */

public class Client {
    private Socket socket = null;
    private int port = 8080;
    private String ip = "localhost";
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Client(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public String sendRequest(String message) {
        String result = null;
        try {
            socket = new Socket(ip, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());
            try {
                System.out.println("Reading...");
                result = (String) inputStream.readObject();
                System.out.println("Server:" + result);
            } catch (ClassNotFoundException e) {
                System.err.println("Data received in unknown format");
            }

            sendMessage(message);

        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    void sendMessage(String msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            System.out.println("Im client! " + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}

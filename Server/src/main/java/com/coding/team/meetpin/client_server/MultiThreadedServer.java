package com.coding.team.meetpin.client_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description of class:
 * <p>
 * Created on 17 Apr 2018 (20:41)
 *
 * @author dawid
 */

public class MultiThreadedServer implements Runnable {

    private ServerSocket serverSocket = null;
    private int port = 8080;
    protected Thread runningThread = null;
    protected boolean isStopped = false;

    public MultiThreadedServer(final int port) {
        this.port = port;
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                System.out.println("Server is listening...");
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            System.out.println("Server accepted connection: " + clientSocket.getInetAddress());
            new ClientWorker(clientSocket).start();
        }
        System.out.println("Server Stopped.");
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }


}

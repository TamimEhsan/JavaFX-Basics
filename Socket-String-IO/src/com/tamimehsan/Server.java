package com.tamimehsan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 12345;

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket clientSocket = null;
        try{
            server = new ServerSocket(PORT);
            for(int i=1; true; i++) {
                final int count = i;
                clientSocket = server.accept();
                Socket finalClientSocket = clientSocket;
                new Thread(){
                    @Override
                    public void run() {
                        try{

                            System.out.println("Client #"+ count + " Connected");
                            // DO YOUR WORK WITH SOCKET HERE
                            // Getting I/O Stream
                            ObjectOutputStream out = new ObjectOutputStream(finalClientSocket.getOutputStream()); // Changed to oos by Tamim
                            ObjectInputStream in = new ObjectInputStream(finalClientSocket.getInputStream());

                            // Simple String Value Sending...
                            out.writeUnshared("Hello World from Server to Client #"+count);

                            // THEN CLOSE IT
                            in.close();
                            out.close();
                            finalClientSocket.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
    }
}
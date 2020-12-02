package com.tamimehsan;
import java.io.*;
import java.net.Socket;

public class Client {
    static final int PORT = 12345;

    public static void main(String[] args) {
        try {

            // DO YOUR WORK WITH SOCKET HERE
            Socket socketServer = new Socket("127.0.0.1", PORT);
            // Getting I/O Stream
            ObjectInputStream in = new ObjectInputStream(socketServer.getInputStream()); // Changed to oos by Tamim
            ObjectOutputStream out = new ObjectOutputStream(socketServer.getOutputStream());
            // Read String Value
            String s= (String) in.readUnshared();
            System.out.println(s);

            // THEN CLOSE IT
            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
    }
}

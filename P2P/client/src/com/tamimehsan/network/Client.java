package com.tamimehsan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket connection;
    private boolean connected = false;

    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private static final String LOCALHOST = "127.0.0.1";
    public static int PORT = 26979;

    private static Client instance;

    private Client(){}

    public static Client getInstance(){
        if( instance == null ){
            instance = new Client();
        }
        return instance;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnection(){
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("started");
                    connection = new Socket(InetAddress.getByName(LOCALHOST),PORT);

                    // Input Stream from Server
                    out = new ObjectOutputStream(connection.getOutputStream());
                    // Output Stream to Server
                    in = new ObjectInputStream(connection.getInputStream());
                    connected = true;
                    System.out.println("connected");
                    getInput();

                }
                catch (ConnectException e) {
                    // Connection Error
                    // Main.showError("Can not create server", 2000);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getInput(){
        while(connected){
            try{
                String message = (String) in.readUnshared();
                System.out.println("Recieved: "+message);
            } catch (IOException e) {
                connected = false;
            } catch (ClassNotFoundException e) {
                connected = false;
            }
        }
    }
    public void send(String s) {
        try{
            out.writeUnshared(s);
        } catch (IOException e){
            connected = false;
        }
    }
    public void closeClient() {
        connected = false;
        if( connection!=null ){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Closing connection");
    }
}

package com.tamimehsan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static Server instance;
    private boolean connected;

    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private ServerSocket server;
    private Socket connection;

    private static final String LOCALHOST = "127.0.0.1";
    public static int PORT = 26979;

    private Server(){}

    public static Server getInstance(){
        if( instance == null ){
            instance = new Server();
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
                try{
                    if( server!=null ){
                        server.close();
                    }
                    server = new ServerSocket(PORT,100);
                    System.out.println("Started Server.\n Waiting for Connection");
                    connection = server.accept();
                    out = new ObjectOutputStream(connection.getOutputStream());
                    in = new ObjectInputStream(connection.getInputStream());
                    connected = true;
                    System.out.println("Connected");
                    getInput();
                }catch (ConnectException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void send(String s){
        try{
            out.writeUnshared(s);

        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
    }
    private void getInput(){
        while (connected){
            try{
                String message = (String) in.readUnshared();
                System.out.println("Recieved: "+message);
            }catch (IOException e) {
                connected = false;
                //e.printStackTrace();
            } catch (ClassNotFoundException e) {
                connected = false;
                //e.printStackTrace();
            }
        }
    }

    public void closeServer() {
        connected = false;
        if( connection!=null ){
            try {
                connection.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Closing connection");
    }
}

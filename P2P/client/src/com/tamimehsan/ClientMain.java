package com.tamimehsan;

import com.tamimehsan.network.Client;

import java.util.Scanner;

public class ClientMain {
    private static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Client.getInstance().setConnection();
        String messageToSend = "Henlo!";
        while( !messageToSend.equalsIgnoreCase("-1") ){
            messageToSend = scanner.nextLine();
            if( com.tamimehsan.network.Client.getInstance().isConnected() ){
                com.tamimehsan.network.Client.getInstance().send(messageToSend);
            } else{
                break;
            }
        }
        com.tamimehsan.network.Client.getInstance().closeClient();
    }
}

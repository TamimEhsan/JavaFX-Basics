package com.tamimehsan;

import com.tamimehsan.network.Server;

import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    public static void main(String[] args) {
	// write your code here
        Server.getInstance().setConnection();
        scanner = new Scanner(System.in);
        String messageToSend = "Henlo";
        while( !messageToSend.equalsIgnoreCase("-1") ){
            messageToSend = scanner.nextLine();
            if( Server.getInstance().isConnected() ){
                Server.getInstance().send(messageToSend);
            } else{
                break;
            }
        }
        Server.getInstance().closeServer();
    }
}

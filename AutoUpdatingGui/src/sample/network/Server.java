package sample.network;


import javafx.application.Platform;
import sample.data.Database;

public class Server {
    private static Server instance;
    private int count = 1;
    private Server(){}

    public static Server getInstance(){
        if( instance == null ){
            instance = new Server();
        }
        return instance;
    }
    public void loop(){
        new Thread(){
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Database.getInstance().addItems("Added from Network. Value="+count);
                                    count++;
                                }
                            }
                    );
                }
            }
        }.start();
    }
}

package sample.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    private static Database instance;
    private ObservableList<String> observableList;
    private Database(){
        observableList = FXCollections.observableArrayList();
    }

    public static Database getInstance(){
        if( instance == null ){
            instance = new Database();
        }
        return instance;
    }
    public ObservableList<String> getObservableList(){
        return observableList;
    }
    public void addItems(String s){
        observableList.add(s);
    }
    public void deleteItems(int index){
        observableList.remove(index);
    }
}

package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.data.Database;

public class Controller {
    @FXML
    private ListView<String>textList;
    @FXML
    private Label selectedLabel;

    private int count = 0;

    public void initialize(){
        textList.setItems(Database.getInstance().getObservableList());
    }

    public void addNew(){
        Database.getInstance().addItems("Added from Gui. Value="+count);
        count = count+1;
    }

    public void deleteList(){
        int index = textList.getSelectionModel().getSelectedIndex();
        if( index!=-1 ){
            Database.getInstance().deleteItems(index);
        }
        index = textList.getSelectionModel().getSelectedIndex();
        if( index!=-1 ){
            selectedLabel.setText(""+index);
        }
    }

    public void updateLabel(){
        int index = textList.getSelectionModel().getSelectedIndex();
        if( index!=-1 ){
            selectedLabel.setText(""+index);
        }
    }

    public ListView<String> getTextList(){
        return textList;
    }
    public Controller getInstance(){
        return this;
    }
}

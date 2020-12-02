# Auto Update GUI

## Basics

In this I will show how to auto update JavaFX gui using ObservableArrayList and List view with worker thread and gui thread.

- Creating a basic gui in scene builder
- Adding references to gui elements in controller
- Updating gui from Application thread and worker thread
- Getting selected index from view 
- Deleting data from View

## Data Flow

![](G:\JavaFX\JavaFX-Basics\AutoUpdatingGui\Assets\dataFlow.PNG)

I usually use this model. I don't if this even has any name or not. But i guess it kinda resembles MVC and Room. 

Here the network never communicated directly with the application or the controller. This is due to the reason we can not update gui from any worker thread. Thus we ensure unwanted change in gui. 

The application interface or gui is controlled by the controller. The application interface is designed in FXML by scene builder. And every business model is implemented in controllers. 

When network brings new update from server , it stores that in storage. And storage notify the controller for a data change and thus the controller will change the gui accordingly.

This flow is pretty safe and is easy to understand for me.

## Project Tree

![](G:\JavaFX\JavaFX-Basics\AutoUpdatingGui\Assets\projectStructure.PNG)

Like before, the files of this one is also divided in several packages for a better environment and look. I have a controllers package for all the controllers. But now I have only one in each package. viewFXML for fxml designs, network package for everything related to networking, data package for storing data.

### Singeltons

Here I have the networking classes and Database (the name has nothing to do with actual sql or no sql database). Like before this ensures that only one entity is created.



## Creating a GUI

![](G:\JavaFX\JavaFX-Basics\AutoUpdatingGui\Assets\gui.PNG)

The gui is pretty basic. In the left side we have a ListView which will used for auto update. We also have two buttons for adding and deleting data to and from view. And two labels for showing which item is selected. This is a demo video showing how this was created in scenebuilder:

Link of demo video:

How to install Scenebuilder:

## Adding reference to gui elements

Notice the fact that we have added a fx:id in some elements of the gui and some has event listeners.

```java
 @FXML
 private ListView<String>textList;
 @FXML
 private Label selectedLabel;
```

We access the elements from controllers by this process. Here the listview is the ref of the table or whatever you call it. You can find a shit ton of tutorials explaining them.

We also have event listeners for buttons and listView.

***Event Listeners*** listens for any specified predefined action and then will act accordingly. LIke when we click a button the onClickListener is called. Then inside that we can do whatever we like. But fxml does this easier for us.

We have three methods

```java
public void initialize();
public void addNew();
public void deleteList();
```

The initialize() method is called when that class is initialized at first. This can be used to initialize variables. 

We will take a pause and check our data storage now.



## Data Storage

It is pretty simple. At first we implemented the basic singleton structure.

```java
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
```

But here we have a observablelist. This will play the main part for auto updating. This is like simple arraylist. And adding and deleting data from it is like arraylist too. So it's simple.

In database class we also have some methods to alter the data inside database ie the observableList.



## Binding observable list with list view

In initialize method of controller class we use this to ask the list view to observe data change inside the observable list

```java
textList.setItems(Database.getInstance().getObservableList());
```

 That's it! We are done with binding.



## Updating the list from gui

By updating I mean updating the observable list. We will never touch the listView directly. May be in JavaFX this is no big deal, But this is good practice and this is must for many structures.

We will update the observable list from application thread once and then from worker thread (this will resemble the networking part).

```java
public void addNew(){
    Database.getInstance().addItems("Added from Gui. Value="+count);
    count = count+1;
}
```

Addnew method is called whenever we press the add new button. Cause we added a event listener to it. Notice we changed only the observable list.

Now the fun part

## Updating list from thread

**WE CAN"T UPDATE OR CHANGE ANYHTING IN GUI FROM THREAD**

from main method we called a loop which will sleep for some time then add data to list. This is to resemble incoming data from socket

```java
Server.getInstance().loop();
```

And the loop has a thread which overrides run

```java
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
                    // We will add the updating code here
                }
            }
        }.start();
    }
```

It is pretty simple thread.

But as we are inside a thread we can't change anything from this. Although it might seem like we are changing observable list. But that has a notify which will be called from thread. Although the application runs perfectly. But it will generate errors.

```java
Platform.runLater(
    new Runnable() {
        @Override
        public void run() {
            Database.getInstance().addItems("Added from Network. Value="+count);
            count++;
        }
    }
);
```



We will use this snippet to update the observable list. Platform.runlater creates a request to application thread to run the code inside later in application thread. There are other ways to update gui too! Like using an animation timer. But as there will less update we can get away with this one easily. 

And we are done with adding data.



## Deleting data from list

List view has a method which will return the selected item index of the list. We will use that and delete that index from the observable list. As we will do this inside application thread we don;t need run later.

```java
public void deleteList(){
    int index = textList.getSelectionModel().getSelectedIndex();
    if( index!=-1 ){
    	Database.getInstance().deleteItems(index);
    }
}
```

It is pretty self explanatory too!

Delete list method will be called when the delete button is clicked. We added some event handlers remember?



That's it we are done for now. Run it and viola!



## Demo

![](G:\JavaFX\JavaFX-Basics\AutoUpdatingGui\Assets\Demo.gif)



Thank you making this far!



> Written by
>
> Md. Tamimul Ehsan
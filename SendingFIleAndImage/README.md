# Data Transfer by Socket

Sending string is already enough hassle in socket, let alone images and files. Files and images are non serializable in java. So we can't send them by sockets. But what we can do is store them in byte array and send that instead. It's as simple as that.

Remarks: For simple tasks we can also send the location of the image and load the image from there. It will work only if the location is available for both the receiver and sender. If it is in same pc it will work, else we will have to use images from internet. 

## Sending Image

Sending image is as easy as this

```java
WritableImage image = new WritableImage(); // get this from anywhere
TransferObject transfer = new TransferObject();
transfer.setImage(image);	
// Now send it to the socket
```

## Reading image

```java
// Read the transfer object here
if( transfer.getState() == 1 ){ // we set state 1 for image
	WritableImage image = transfer.getImage();
}
```

## Sending File

At first we will need to get the file by Filechooser

```java
Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
File file = fileChooser.showOpenDialog(stage);
if( file == null || file.exists() == false ){
	return;
}
```

The file chooser needs the stage as a parameter. We can get the state from mouse event or saving the reference to the primaryStage and use that here.

We also check for cancel action and return for that.

```java
TransferObject transferObject = new TransferObject();
transferObject.setFile(file);
// Send the object by socket here
```

And that's it

## Saving or reading file

We read the name of the file from object and create the full path for the file. 

destination folder can be something like "C:\\Users\User\Desktop"

```java
// Read the object from network
if(transferObject.getState() == 2){
	String destinationFolder = "Your prefered saving location here"
    String outputfile = destinationFolder +"\\"+ transferObject.getFilename();
    System.out.println(outputfile);
    File dstfile = new File(outputfile);
    FileOutputStream fileOutputStream = new FileOutputStream(dstfile);
    fileOutputStream.write(transferObject.getFileData());
}
```

## Adding filter to file chooser

Sometimes we only want specific type of file to be available to user.

For images we can use the below codes for that.

```
FileChooser fileChooser = new FileChooser();
fileChooser.setTitle("Upload File Path");
fileChooser.getExtensionFilters().addAll(
	new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png")
);
```

And other type of filers can be like

``` java
fileChooser.getExtensionFilters().addAll(
     new FileChooser.ExtensionFilter("Text Files", "*.txt"),
     new FileChooser.ExtensionFilter("HTML Files", "*.htm")
);
```



Thank you for making this far.

> Written by
>
> Md. Tamimul Ehsan
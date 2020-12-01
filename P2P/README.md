# SocketIO in Java

## Basics

Socket is used for communication between different users via internet or localhost. In this smol application I will create a ui less cmd chat application. 

You can learn about 

- Setting up a connection
- Setting IO streams
- Reading and Writing
- Singleton

## Singleton

Singleton is a class where you can only create only one instance. This is a kind of Java Design pattern. It is used for networking, database connection etc.

### Folder Structure

The networking stuffs are in a new package called network. And the server is inside network. This is to ensure clean project structure.

![](https://raw.githubusercontent.com/TamimEhsan/JavaFX-Basics/master/P2P/Assets/folder.PNG)



### Declaring a singleton

![](https://raw.githubusercontent.com/TamimEhsan/JavaFX-Basics/master/P2P/Assets/singletonDeclaration.PNG)

Here, in server class we declared the constructor as private , so no class can create any object. And we get the instance by calling the get instance method.

To call any method we will use this

```java
Server.getInstance().isConnected()
```

## Server

### Contents of Server

```java
	public static Server instance;
```

This is the object instance to be shared with every class.

```
	private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
```

out and in are the IO streams which will be used to send and receive data. Sir might use Buffered Reader class. But I love this.

```
	private ServerSocket server;
    private Socket connection;
```

Here comes the main server part. The server socket creates a server. And the Socket connection will establish connection

```
	private static final String LOCALHOST = "127.0.0.1";
    public static int PORT = 26979;
```

LOCALHOST and PORT are the address of the server. 127.0.0.1 is the address within a local network in a address there might be different port. We are going to listen at port 2679. This can be anything.



#### Setting up a connection

```
Server.getInstance().setConnection();
```

From main we call this method. And the method is->

![](https://raw.githubusercontent.com/TamimEhsan/JavaFX-Basics/master/P2P/Assets/serverSet.PNG)

The method runs a new thread. Because we don't know how much time it will be needed to establish a connection

```
server = new ServerSocket(PORT,100);
```

At first we create a server in PORT with backlog 100. This means a maximum of 100 users at once.

```
connection = server.accept();
```

When the client side will connect to this server the connection will be established.

```
out = new ObjectOutputStream(connection.getOutputStream());
in = new ObjectInputStream(connection.getInputStream());     
```

Then we accept the input streams from the connection. After this we don't need connection untill the end. Here we will send a out signal first which will be received by client as in signal. And the out signal of client will be received as in stream.

```
getInput();
```

Then we call the getInput(); method. remember we are still inside a thread.



### Listening for input

The problem is we don't know when there will be a signal. So we always need to listen for it.

```
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
```

The method uses a gameLoop which runs on boolean value connected.  

```
String message = (String) in.readUnshared();
```

We want to read a string from input stream. Unshared ensures that no caching will happen. This is good for big data send. But the received object is Object. So we cast to String and output it to console. And if any error occurs we break out of loop and set connection = false and exits program.

### Sending Message

```
if( Server.getInstance().isConnected() ){
	Server.getInstance().send(messageToSend);
}
```

We send message by the above snippet. We check if the connection is intact, then send the message after reading from system.in.

The send method in server is below:

```
public void send(String s){
    try{
    	out.writeUnshared(s);
    } catch (IOException e) {
    e.printStackTrace();
    	connected = false;
    }
}
```

We take the string and send to stream. We used unshared here too. 

But

```
out.writeObject(s);
out.reset();
```

works too.

### Closing server

```
Server.getInstance().closeServer();
```

```
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
```

We check if connection is null or not. then close the connection.

## Client

In client there is no Server socket. Cause we are joining session of the host. 

Instead of 

```
private ServerSocket server;
private Socket connection;
```

we have just.

```
private Socket connection;
```

### Setting up connection

```
connection = new Socket(InetAddress.getByName(LOCALHOST),PORT);
```

In the setConnection method like server, we try to connect to the LOCALHOST address in port PORT! And the rest are exactly the same.



## Using the system

At first run the serverJava then the client java. Otherwise client won't find anything to connect.

![](https://raw.githubusercontent.com/TamimEhsan/JavaFX-Basics/master/P2P/Assets/Connection.gif)

Then we can send messages using the terminal. UwU

![](https://raw.githubusercontent.com/TamimEhsan/JavaFX-Basics/master/P2P/Assets/Working.gif)





> Written by
>
> Md. Tamimul Ehsan

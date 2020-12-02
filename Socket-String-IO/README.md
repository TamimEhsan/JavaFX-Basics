# Networking

> By [Md. Jehadul Karim](www.github.com/MJKSabit)

Prerequisite:

- Threading

Recommended:

- Input & Output Stream

So, how do you do Networking. Lets dive into the **basics of networking**.

## Basics of Networking

First of all you need to understand how networking is done. There are two types of connection (as far as I know):

1. TCP
2. UDP

In this tutorial, I am only going to talk about **TCP Networking**. In TCP networking there exists a **server** which creates connection between two sockets. **A network connection** should contain **two sockets**, one at **Server-End** and other at **Client-End**.

## Socket Networking

A **Socket** exposes some interface to send and receive data from one socket to other when **a network** is established. To relate, you can consider `cin` as the interface to get (*read*) and `cout` as the interface to write (*send*). 

### Server-Client Networking

A client can not directly communicate with other client directly. They need to **get connected first**. After getting connected, there will be **two Sockets** at both ends (*Server and Client*).

> There is no difference between a server-side socket and client-side socket.

The socket read/write interface are relative to the side socket is in. These are not absolute. For example, If I **write** on one of the sockets (eg. Client-SIde), then I must **read** from the other socket (eg. Server-Side). You should not confuse to use **write** interface on both side to access the data.

| Socket On One Side    | Serial | Socket On other Side  |
| --------------------- | :----: | --------------------- |
| write(data)           |  -->   | read() : returns data |
| read() : returns data |  <--   | write(data)           |



# Socket Networking in JAVA

---

## Prework

Before using socket, we have to connect two sockets at two ends. The basic way of doing this is to **wait for other to connect** (*listening*). It is done at the server side. To listen for a connection in server side, we have to use a java class, `ServerSocket`, Using this class we can start a connection and two sockets to communicate with.

### Initialization

To start a ServerSocket, you first have to create an instance of it with the **port** you want to serve to.
`ServerSocket server = new ServerSocket(PORT);`

> PORT can be any positive integer from 0 to 2^16-1, But my suggestion would be using port higher than 10000. You have to **note down the PORT** as the same PORT will be used in the client side.

### Connecting...

After the instance is being created, you can now **wait** for client to be connected on the same port.
`Socket clientSocket = server.accept();`

### Connected

When a client is connected to the server, we will have the the socket in the `clientSocket` and use it to send and receive data to and from client. 

### Close ServerSocket

You should always close the `ServerSocket` to release the PORT to be used by other applications by calling:
`server.close()`

### Connect from Client Side

Just initialize a `Socket` instance, just use `127.0.0.1` as hostname and the same PORT that was used in Server Side.
`Socket socketServer = new Socket("127.0.0.1", PORT);`

### Current Code

`Server.java`

```java
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 12345;
    
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket clientSocket = null;
        try{
        	server = new ServerSocket(PORT);
        	clientSocket = server.accept();
            server.close();
           	System.out.println("Client Connected");
            // DO YOUR WORK WITH SOCKET HERE
            // ...
            // THEN CLOSE IT
            clientSocket.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
    }
}
```

`Client.java`

```java
import java.net.Socket;

public class Client {
    static final int PORT = 12345;
	
    public static void main(String[] args) {
    	try {
            Socket socketServer = new Socket("127.0.0.1", PORT);
            // DO YOUR WORK WITH SOCKET HERE
            // ...
            // THEN CLOSE IT
            socketServer.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
	}
}


```

> You must **allow parallel run** from [ Run ] > [ Edit Configuration ] and run two classes in separate projects as they will have be run simultaneously
>
> 1. Run Server First
> 2. Then Run Client

## Socket

Networking through `Socket` includes **Sending** and **Receiving** data as I have mentioned earlier. This can be done using `OutputStream` and `InputStream`. `Socket` class has methods for directly accessing these steams.

`InputSteam in = clientSocket.getInputSteam();` 
`OutputStream out = serverSocket.getOutputSteam();`

`OutputStream` and `InputStream` has limited numbers of methods to send and receive data. Simple `in.read()` will read an `int` and `out.write(INT)` will send an `INT` to the other side.

> Steams must be **closed** by calling `close()`

###  Current Code

`Server.java`

```java
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 12345;
    
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket clientSocket = null;
        try{
        	server = new ServerSocket(PORT);
        	clientSocket = server.accept();
            server.close();
           	System.out.println("Client Connected");
            // DO YOUR WORK WITH SOCKET HERE
            // Getting I/O Stream
            OutputStream out = clientSocket.getOutputSteam();
            InputStream in = clientSocket.getInputSteam();
            
            // Simple Integer Value Sending...
            int DATA = 12345;
            out.write(DATA); // CHANGE 12345 to test
            System.out.println("DATA sent: " + DATA);
            
            // THEN CLOSE IT
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
    }
}
```

`Client.java`

```java
import java.net.Socket;

public class Client {
    static final int PORT = 12345;
	
    public static void main(String[] args) {
    	try {
            Socket socketServer = new Socket("127.0.0.1", PORT);
            // DO YOUR WORK WITH SOCKET HERE
            
            // Getting I/O Stream
            OutputStream out = socketClient.getOutputSteam();
            InputStream in = socketClient.getInputSteam();
            
            // Simple Integer Value Sending...
            int DATA;
            DATA = in.read();
            System.out.println("DATA received: " + DATA);
            
            // THEN CLOSE IT
            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
	}
}


```

## Reader & Writer

You normally don't use Input & Output Stream directly. We wrap them within another class to get some extra functionalities. For example, we could have directly used `System.in` instead of using `new Scanner(System.in)`, But the `Scanner` provides much more functionalities to access data.

Here we will use `BufferedReader` and `BufferedWriter`. They provide functions to directly send and receive `String` over the I/O Streams.

### BufferedReader

This one is used with `InputStream`, to get an instance of this, use:
`BufferedReader reader = new BufferedReader(new InputStreamReader(in));`

`BufferedReader` has a method `readLine()` to directly read a `String` from the stream.
`String input = reader.readLine();`

### BufferedWriter

This one is used with `OutputStream`, to get an instance of this, use:
`BufferedWriter writer = new BufferedWriter(new InputStreamWriter(out));`

`BufferedReader` has a method `write(String)` to directly send a `String` from the stream. Be careful to include a `\n` in the end of the String or call `newLine()` method, else `readLine()` from the otherside will not be able to read the steam,

`writer.write("Hello World!");`
`write.newLine()`

### Current Code

`Server.java`

```java
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 12345;
    
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket clientSocket = null;
        try{
        	server = new ServerSocket(PORT);
        	clientSocket = server.accept();
            server.close();
           	System.out.println("Client Connected");
            // DO YOUR WORK WITH SOCKET HERE
            // Getting I/O Stream
            OutputStream out = clientSocket.getOutputSteam();
            InputStream in = clientSocket.getInputSteam();
            
            // Simple String Value Sending...
            BufferedWriter writer = new BufferedWriter(new InputStreamWriter(out));
            writer.write("Hello World from Server.");
            writer.newLine();
            
            // THEN CLOSE IT
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
    }
}
```

`Client.java`

```java
import java.net.Socket;

public class Client {
    static final int PORT = 12345;
	
    public static void main(String[] args) {
    	try {
            Socket socketServer = new Socket("127.0.0.1", PORT);
            // DO YOUR WORK WITH SOCKET HERE
            
            // Getting I/O Stream
            OutputStream out = socketClient.getOutputSteam();
            InputStream in = socketClient.getInputSteam();
            
            // Simple Integer Value Sending...
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            // Read String Value
            System.out.println(reader.readLine());
            
            // THEN CLOSE IT
            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
	}
}


```

## 

You can try sending different string, or even multiple strings. Just use these methods and you are good to go...

## Multi-client Network

This is where threading comes in. A server might need to be connected to multiple client at the same time. To connect with other client, server must always **listen** using `server.accept()`, it is a **BLOCKING** function. So, to perform other actions with other connected clients, **All clients must be in different threads from `server.accept()`**.

> **BLOCKING: **Example, `scanner.readLine()`, program will not proceed until input has been given. **Blocks **the program.

To connect multiple client, we can use java `Therad` like below:

### Current Code

`Server.java`

```java
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 12345;
    
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket clientSocket = null;
        try{
        	server = new ServerSocket(PORT);
            for(int i=1; true; i++) {
                clientSocket = server.accept();
                new Thread(){
                    Socket socket = clientSocket;
                    System.out.println("Client #"+i" Connected");
            		// DO YOUR WORK WITH SOCKET HERE
         		   	// Getting I/O Stream
            		OutputStream out = socket.getOutputSteam();
            		InputStream in = socket.getInputSteam();
            
            		// Simple String Value Sending...
        	    	BufferedWriter writer = new BufferedWriter(new InputStreamWriter(out));
    	        	writer.write("Hello World from Server to Client #"+i);
	            	writer.newLine();
            
            		// THEN CLOSE IT
            		in.close();
            		out.close();
            		socket.close();
                }.start();
            }
        	
            server.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
    }
}
```

`Client.java`

```java
import java.net.Socket;

public class Client {
    static final int PORT = 12345;
	
    public static void main(String[] args) {
    	try {
            Socket socketServer = new Socket("127.0.0.1", PORT);
            // DO YOUR WORK WITH SOCKET HERE
            
            // Getting I/O Stream
            OutputStream out = socketClient.getOutputSteam();
            InputStream in = socketClient.getInputSteam();
            
            // Simple Integer Value Sending...
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            // Read String Value
            System.out.println(reader.readLine());
            
            // THEN CLOSE IT
            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            // Ignored for Simplicity
        } finally {
            // Free up Resources by CLOSING them
        }
	}
}


```

## 

Thats all for Today. 

**Happy Coding**
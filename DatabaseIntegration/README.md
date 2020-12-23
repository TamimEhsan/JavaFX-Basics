# Database Integration

### Downloading SQLite

Go to [SQLite official download page](https://www.sqlite.org/download.html). And download the suitable version. For windows download Precompiled Binaries for windows , then either 32/64/86 bit.

Then set up a variable path to sqlite in environment variables. Honestly I forgot if this is actually necessary for working with JDBC. Will have to check again. 

### Downloading JDBC

[Download JDBC for windows](https://github.com/TamimEhsan/Tuition-E/blob/master/Dependencies/sqlite-jdbc-3.32.3.2.jar)

Then Extract that to any convenient folder and Then inside your IDE add that as a dependency to project structure or module or whatever you have.

### Creating the database:

Interesting thing with SQLite is that when you try to access some database that isn't there it will create it automatically for you

```java
private Connection connection;
///
connection = DriverManager.getConnection("jdbc:sqlite:Database\\TuitionE.db");
```

That's it!

Here the jdbc:sqlite is usual syntax, then the next part is relative to the root folder. Here is a screenshot showing the location of the database. And the extension of database is db. 

Before running the program

![Before](https://github.com/TamimEhsan/JavaFX-Basics/blob/master/DatabaseIntegration/Assets/beforeDB.PNG)

Notice that the db folder is empty. And if we want to create like this create the folder Database yourself.

![After](https://github.com/TamimEhsan/JavaFX-Basics/blob/master/DatabaseIntegration/Assets/afterDB.PNG)

And after the programme we see the db created. And notice the sign '?'. After we have created the table the sign will change.

### Creating a table

```java
 try{
    connection = DriverManager.getConnection("jdbc:sqlite:Database\\UserDB.db");
    Statement statement = connection.createStatement();
    statement.execute("CREATE TABLE IF NOT EXISTS users " +
            "(ID INTEGER PRIMARY KEY,username TEXT, taskDesc TEXT ,password TEXT)");
}catch (SQLException e){
    System.out.println("error");
}
```

Each database model has their own style. JDBC for instance works with this statements. You create a new statement from the connection. And execute the sql command with that statement.

Here we used the sql command CREATE TABLE IF NOT ESIXSTS. This ensures that we have the required table. It will not create table again if it is already there.

### SQLite Commands

SQLite commands are little weak compare to SQL commands. You can't do many type of queries like SQL here. But it will get the job done 



#### Insert Statement

```java
String sql = "INSERT INTO users (username, password) " +
                "VALUES ( '"+name+"' , '"+deadline+"' )";
try(Statement statement = connection.createStatement()){
    statement.execute(sql);
} catch (SQLException e){
    e.printStackTrace();
}
```



The sql string looks weird, because we have to write this actually

```sqlite
INSERT INTO users (username,password)
VALUES( 'hereItIsAVariable' , 'ThisIsAVariableToo' )
```

So inorder to write variables we have to write like that. If you have any better approach please mention.

#### SELECT Statement

Getting all data from users

```sqlite
SELECT * FROM users
```

Getting a specific data from users

```sqlite
SELECT * FROM users
WHERE username = 'TamimEhsan'
```

#### Looping result

```java
String sql2 = "SELECT * FROM users"
ResultSet resultSet = statement.executeQuery(sql2);
while(resultSet.next()){
    System.out.println(resultSet.getString(2)+" "+resultSet.getString(3));
}
```



#### DELETE Statement

```sqlite
DELETE FROM users
WHERE username = 'Tamim Ehsan'
```

Don't ever forget to write the WHERE clause, else it will delete all data from table users.

### Extra resources

[Music Database](https://github.com/hishamcse/JDBC_SQLite_EXAMPLE_CODES) - By [Syed Jarullah Hisham](https://github.com/hishamcse)



So that's it for today!

Thank you

> Written By
>
> Md. Tamimul Ehsan

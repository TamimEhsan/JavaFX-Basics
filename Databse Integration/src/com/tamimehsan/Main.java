package com.tamimehsan;

import java.sql.*;

public class Main {
    private static Connection connection;
    public static void main(String[] args) {
	// write your code here
        try{
            // connecting Database
            connection = DriverManager.getConnection("jdbc:sqlite:Database\\Users.db");
            // A Statement is an interface that represents a SQL statement.
            Statement statement = connection.createStatement();
            // Creating table if not exists Will only trigger once
            statement.execute("CREATE TABLE IF NOT EXISTS users " +
                    "(ID INTEGER PRIMARY KEY,username TEXT, password TEXT )");
            // Inserting new Value
            String sql = "INSERT INTO users (username, password) " +
                    "VALUES ( 'JoyaAhsan', 'TamimEhsan' )";
           // statement.execute(sql); // Uncomment this to insert this
            // Selecting all data
            String sql2 = "SELECT * FROM users";
            ResultSet resultSet2 = statement.executeQuery(sql2);
            while(resultSet2.next()){
                System.out.println(resultSet2.getString(2)+" "+resultSet2.getString(3));
            }
            System.out.println("===================");
            // Selecting Specific data
            String sql3 = "SELECT * FROM users WHERE username='TamimEhsan'";
            ResultSet resultSet3 = statement.executeQuery(sql3);
            while(resultSet3.next()){
                System.out.println(resultSet3.getString(2)+" "+resultSet3.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

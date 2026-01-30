package com.revconnect.database.config;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
    private static final String URL="jdbc:mysql://localhost:3306/revconnect";
    private static final String USER="root";
    private static final String PASSWORD="Siddhi@24";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
    }
}

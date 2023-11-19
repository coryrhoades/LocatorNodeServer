package com.example.locstreamserver;
import java.sql.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.example.locstreamserver.databaseConnectionInfo;
@SpringBootApplication
public class LocstreamServerApplication {
    //static String url = "jdbc:sqlserver://192.168.50.243;databaseName=LocatorStreamServer;trustServerCertificate=true;";
    //static String dbuser = "sa";
    //static String dbpass = "C@llth3h0ff";

    public static void main(String[] args) {
        //databaseTestConnection();
        SpringApplication.run(LocstreamServerApplication.class, args);


    }

    public static ResultSet databaseExecuteQuery(String query) {
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            System.out.println("Connected to Microsoft SQL Server. (databaseExecuteQuery)");

            //execute sql query here
            try {
                Statement stmt = connection.createStatement();
                ResultSet result = stmt.executeQuery(query);
                System.out.println("Query executed successfully");

                connection.close();
                System.out.println("Successfully executed query: '" + query + "' - Closing connection. ");
                return result;
            } catch (SQLException x) {
                System.out.println("SQL Connection succeeded. Error executing query: '" + query + "'");

                connection.close();
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to Microsoft SQL Server: ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + query);
            return null;
        }
    }

    public static void databaseTestConnection() {
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            System.out.println("TEST: Connected to Microsoft SQL Server.");
            connection.close();
            System.out.println("TEST: Connection to Microsoft SQL Server closed.");

        } catch (SQLException e) {
            System.out.println("TEST: Error connecting to Microsoft SQL Server: ");
            e.printStackTrace();
        }
    }


}

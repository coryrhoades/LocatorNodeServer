package com.example.locstreamserver.repository;
import com.example.locstreamserver.databaseConnectionInfo;

import java.sql.*;

public class locatorDatabaseQueries {
    String url = "jdbc:sqlserver://192.168.50.243;databaseName=LocatorStreamServer;trustServerCertificate=true;";
    String dbuser = "sa";
    String dbpass = "C@llth3h0ff";
    String databaseName = "LocatorStreamServer";
    public locatorDatabaseQueries() {
        databaseConnectionInfo database = new databaseConnectionInfo();
        url = database.getUrl();
        dbuser = database.getDbuser();
        dbpass = database.getDbpass();
        databaseName = database.getDatabaseName();

    }

    public static ResultSet databaseGetQueryResult(String query) {
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
                //System.out.println("Result from LocatorCountByAccount: " + result.getString(1));
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
    public static void databaseExecuteQuery(String query) { //does not expect a result to be returned
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

            } catch (SQLException x) {
                System.out.println("SQL Connection succeeded. Error executing query: '" + query + "'");

                connection.close();

            }

        } catch (SQLException e) {
            System.out.println("Error connecting to Microsoft SQL Server: ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + query);

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

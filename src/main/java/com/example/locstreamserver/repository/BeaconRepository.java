package com.example.locstreamserver.repository;


import com.example.locstreamserver.databaseConnectionInfo;
import com.example.locstreamserver.model.Beacon;
import com.example.locstreamserver.model.Event;
import com.example.locstreamserver.model.ownerChange;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import static java.lang.System.getProperty;

@Component
public class BeaconRepository {
    String beaconTable = "Beacons";
    String url;
    String dbuser;
    String dbpass;
    String databaseName;
    public BeaconRepository() {
        //setup database info
        databaseConnectionInfo database = new databaseConnectionInfo();
        url = database.getUrl();
        dbuser = database.getDbuser();
        dbpass = database.getDbpass();
        databaseName = database.getDatabaseName();

    }
    public int addBeacon(String beaconName, String macAddress) { //insert beacon into database, return beacon ID
        //validateInput
        ArrayList<Beacon> arr = new ArrayList<>();
        ResultSet result = null;
        //build sql string
        String queryString = "INSERT INTO " + beaconTable + " (beaconName, macAddress) values ('"+beaconName+"','" + macAddress + "');";
        //System.out.println(queryString);

        //connect to sql database
        //execute query
        //returns all beacon objects
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            System.out.println("Connected to Microsoft SQL Server. (addBeacon())");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(queryString);
            System.out.println("Query String: " + queryString);
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in addBeacon(): ");
            if (e.getMessage().contains("Violation of UNIQUE KEY constraint 'UQ_Beacons_macAddress'")) {
                // Handle the violation of the unique key constraint
                System.out.println("A record with the same macAddress already exists.");
                return -2;
            } else {
                e.printStackTrace();
            }

            System.out.println("Query failed to execute: " + queryString);
            return 0;
        }
        //disconnect from database
        arr = getBeaconByMac(macAddress);
        System.out.println("array list index 1: " + arr);
        if (!arr.isEmpty()) {
            Beacon beacon = arr.get(0);
            return beacon.getBeaconId();
        } else {
            return 0;
        }
    }
    public void deleteBeacon(String macAddress) {
        //build sql string
        //connect to sql database
        //execute query
        //disconnect from database
    }
    public ArrayList<Beacon> getBeacons() {
        String queryString = "SELECT * FROM " + beaconTable;
        ResultSet result = null;
        String returnString = "";
        ArrayList<Beacon> arr = new ArrayList<>();

        //returns all beacon objects
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            //System.out.println("Connected to Microsoft SQL Server. (getBeacons())");
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);
            //System.out.println("Query String: " + queryString);
            while (result.next()) {
                Beacon beacon = new Beacon();

                beacon.setBeaconId(result.getInt(1));
                //returnString +=  "Beacon ID: " + result.getInt(1) + getProperty("line.separator");
                //System.out.println("Beacon ID: " + result.getInt(1));
                beacon.setBeaconName(result.getString(2));
                //returnString +=  "Beacon Name: " + result.getString(2) + getProperty("line.separator");
                //System.out.println("Beacon Name: " + result.getString(2));
                beacon.setMacAddress(result.getString(3));
                //returnString +=  "MAC Address: " + result.getString(3) + getProperty("line.separator");
                //System.out.println("MAC Address: " + result.getString(3));
                beacon.setCurrentOwner(result.getString(4));
               // returnString +=  "Current Owner: " + result.getString(4) + getProperty("line.separator");
                //System.out.println("Current Owner: " + result.getString(4));
                arr.add(beacon);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in getBeacons(): ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);
            return null;
    }
        //System.out.println(arr);
        return arr;
    }


    //getBeaconById

    public ArrayList<Beacon> getBeaconById(String id) {
        System.out.println("Variable from request is: " + id);
        String queryString = "SELECT * FROM " + beaconTable + " WHERE beaconId = '" + id + "'";
        ResultSet result = null;
        ArrayList<Beacon> arr = new ArrayList<>();

        //returns all beacon objects
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            //System.out.println("Connected to Microsoft SQL Server. (getBeaconById())");
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);
            System.out.println("Query String: " + queryString);
            while (result.next()) {
                Beacon beacon = new Beacon();

                beacon.setBeaconId(result.getInt(1));
                //returnString +=  "Beacon ID: " + result.getInt(1) + getProperty("line.separator");
                //System.out.println("Beacon ID: " + result.getInt(1));
                beacon.setBeaconName(result.getString(2));
                //returnString +=  "Beacon Name: " + result.getString(2) + getProperty("line.separator");
                //System.out.println("Beacon Name: " + result.getString(2));
                beacon.setMacAddress(result.getString(3));
                //returnString +=  "MAC Address: " + result.getString(3) + getProperty("line.separator");
                //System.out.println("MAC Address: " + result.getString(3));
                beacon.setCurrentOwner(result.getString(4));
                // returnString +=  "Current Owner: " + result.getString(4) + getProperty("line.separator");
                //System.out.println("Current Owner: " + result.getString(4));
                arr.add(beacon);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in getBeacons(): ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);
            return null;
        }
        System.out.println(arr);
        return arr;
    }

    public ArrayList<Beacon> getBeaconByMac(String mac) {
        System.out.println("Variable from request is: " + mac);
        String queryString = "SELECT * FROM " + beaconTable + " WHERE macAddress = '" + mac + "'";
        ResultSet result = null;
        ArrayList<Beacon> arr = new ArrayList<>();

        //returns all beacon objects
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            System.out.println("Connected to Microsoft SQL Server. (getBeaconByMac())");
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);
            System.out.println("Query String: " + queryString);
            while (result.next()) {
                Beacon beacon = new Beacon();

                beacon.setBeaconId(result.getInt(1));
                //returnString +=  "Beacon ID: " + result.getInt(1) + getProperty("line.separator");
                //System.out.println("Beacon ID: " + result.getInt(1));
                beacon.setBeaconName(result.getString(2));
                //returnString +=  "Beacon Name: " + result.getString(2) + getProperty("line.separator");
                //System.out.println("Beacon Name: " + result.getString(2));
                beacon.setMacAddress(result.getString(3));
                //returnString +=  "MAC Address: " + result.getString(3) + getProperty("line.separator");
                //System.out.println("MAC Address: " + result.getString(3));
                beacon.setCurrentOwner(result.getString(4));
                // returnString +=  "Current Owner: " + result.getString(4) + getProperty("line.separator");
                //System.out.println("Current Owner: " + result.getString(4));
                arr.add(beacon);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in getBeacons(): ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);
            return null;
        }
        System.out.println(arr);
        return arr;
    }

    public ArrayList<ownerChange> getOwnershipChanges(){
        String startDate = "" + Instant.now().minus(Duration.ofDays(30));
        String endDate = "" + Instant.now();
        return getOwnershipChanges(startDate, endDate);
    }

    public ArrayList<ownerChange> getOwnershipChanges(String startDate, String endDate){
        ArrayList<ownerChange> arr = new ArrayList<>();
        ResultSet result = null;

        String queryString = "select top 200 * from ownerChangeLog order by timeStamp desc";
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());

            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);

            while (result.next()) {
                Beacon beacon = new Beacon();
                ownerChange owner = new ownerChange();
                owner.setNewOwner(result.getInt("newOwner"));
                owner.setTimestamp(result.getString("timeStamp"));
                owner.setBeaconId(result.getInt("beaconId"));
                owner.setPreviousOwner(result.getInt("previousOwnerId"));

                arr.add(owner);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in getOwnershipChanges: ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);
            return null;
        }
        //System.out.println(arr);
        return arr;
    }

    public static void logOwnershipChange(int beaconId, int newOwner) {
        String time = "" + Instant.now();
        logOwnershipChange(beaconId, newOwner, time);
        //
    }

    public static void logOwnershipChange(int beaconId, int newOwner, String time) {
        int oldOwner = getCurrentOwnerById(beaconId);
        // INSERT INTO ownerChangeLog VALUES (1, 123, 456, '2023-12-03T14:30:00')
        String queryString = "INSERT INTO ownerChangeLog (previousOwnerId, newOwner, timeStamp, beaconId) VALUES (" + oldOwner + ", " + newOwner + ", '" + time + "', " + beaconId + ");";

        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(queryString);
            System.out.println("logOwnershipChange Query String: " + queryString);
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in setOwner(): ");
            e.printStackTrace();
        }
    }


public static void setOwner(int locatorId, int beaconId) {
        /*
        UPDATE beacons
SET currentOwner = 1
WHERE beaconId = 100;

         */
    String queryString = "UPDATE beacons SET currentOwner = " + locatorId + " where beaconId = " + beaconId + ";";

//        System.out.println("setOwner query string: " + queryString);


    try {
        databaseConnectionInfo sqlserver = new databaseConnectionInfo();
        Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
        //System.out.println("Connected to Microsoft SQL Server. (addBeacon())");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(queryString);
        System.out.println("setOwner Query String: " + queryString);
        connection.close();
    } catch (SQLException e) {
        System.out.println("SQL Error in setOwner(): ");
        e.printStackTrace();
    }


}



    public static int getCurrentOwnerById(int beaconId) {
        int ownerId = -1;

        ResultSet result = null; //where to store the SQL results

        String queryString = "SELECT currentOwner FROM beacons WHERE beaconId = '" + beaconId + "'";

        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);


            while (result.next()) {
                if(result.getInt("currentOwner") > 0) {
                    ownerId = result.getInt("currentOwner");
                } else {
                    ownerId = -1;
                }
            }
            //System.out.println("Current owner for beaconID " + beaconId + " is " + ownerId);
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in getCurrentOwnerById(): "+"Query failed to execute: " + queryString);
            e.printStackTrace();
        }
        return ownerId;

    }


}

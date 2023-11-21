package com.example.locstreamserver.repository;

import com.example.locstreamserver.databaseConnectionInfo;
import com.example.locstreamserver.model.Beacon;
import com.example.locstreamserver.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventRepository {
    String eventsTable = "BeaconEvents";
    String url;
    String dbuser;
    String dbpass;
    String databaseName;

    public EventRepository() {
        //setup database info
        databaseConnectionInfo database = new databaseConnectionInfo();
        //url = database.getUrl();
        //dbuser = database.getDbuser();
        //dbpass = database.getDbpass();
        //databaseName = database.getDatabaseName();



        }


    public void addEvents(List<Event> events) {
        String queryString = "INSERT INTO BeaconEvents(locatorId, beaconId, signalStrength, detectTime) VALUES";
        Event lastEvent = events.get(events.size() - 1);
        System.out.println("Last Event: " + lastEvent);
                //	Insert into BeaconEvents(locatorId, beaconId, signalStrength, detectTime) values(1, 1, 54, CONVERT(DATETIME, '2022-05-08 14:30:45', 120));
        //add values to the string
        for (Event event : events) {

            queryString += event.getDBInsertString();

            if(event != lastEvent) {
                queryString += ",";
            }

        }
        queryString += ";";
        System.out.println("addEvents query string: " + queryString);

        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            System.out.println("Connected to Microsoft SQL Server. (addEvents())");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(queryString);
            System.out.println("Query String: " + queryString);
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL Error in addEvents(): ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);

        }




    }

    public ArrayList<Event> getLast100EventsById(String beaconId) {
        ArrayList<Event> events = new ArrayList<Event>();
        //SELECT TOP 100 * FROM (tablevar) ORDER BY ID DESC;
        ResultSet result = null; //where to store the SQL results
        //ArrayList<Beacon> arr = new ArrayList<>(); //where to store the results prior to returning them
        //build sql string
        String queryString = "SELECT TOP 100 * FROM " + eventsTable + " WHERE beaconId = '" + beaconId + "' ORDER BY eventId DESC";

        //returns last 100 events
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            System.out.println("Connected to Microsoft SQL Server. (getLast100EventsById())");
            Statement stmt = connection.createStatement();
            //result = stmt.executeQuery(queryString);
            System.out.println("result = " + result);
            System.out.println("Query String: " + queryString);

            while (result.next()) {
                Event event = new Event();
                event.setEventId(result.getInt("eventId"));
                event.setLocatorId(result.getInt("locatorId"));
                event.setBeaconId(result.getInt("beaconId"));
                event.setSignalStrength(result.getInt("signalStrength"));
                event.setTimeStamp(result.getString("detectTime"));
                System.out.println(event.toString());
                events.add(event);
            }
            connection.close();

        } catch (SQLException e) {
            System.out.println("SQL Error in getLast100EventsById(): "+"Query failed to execute: " + queryString);
            e.printStackTrace();
        }

        if(events!=null) {
            return events;
        } else {
            return null;
        }

    }



    public ArrayList<Event> getLast100Events() {
            ArrayList<Event> events = new ArrayList<Event>();
             //SELECT TOP 100 * FROM (tablevar) ORDER BY ID DESC;
            ResultSet result = null; //where to store the SQL results
             //ArrayList<Beacon> arr = new ArrayList<>(); //where to store the results prior to returning them
             //build sql string
            String queryString = "SELECT TOP 100 * FROM " + eventsTable + " ORDER BY eventId DESC";

        //returns last 100 events
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            System.out.println("Connected to Microsoft SQL Server. (getLast100Events())");
            Statement stmt = connection.createStatement();
            //result = stmt.executeQuery(queryString);
            System.out.println("result = " + result);
            System.out.println("Query String: " + queryString);



            while (result.next()) {
                Event event = new Event();
                event.setEventId(result.getInt("eventId"));
                event.setLocatorId(result.getInt("locatorId"));
                event.setBeaconId(result.getInt("beaconId"));
                event.setSignalStrength(result.getInt("signalStrength"));
                event.setTimeStamp(result.getString("detectTime"));
                System.out.println(event.toString());
                events.add(event);
            }
            connection.close();

        } catch (SQLException e) {
            System.out.println("SQL Error in getLast100Events(): "+"Query failed to execute: " + queryString);
            e.printStackTrace();
        }

        if(events!=null) {
            return events;
        } else {
            return null;
        }

    }



}

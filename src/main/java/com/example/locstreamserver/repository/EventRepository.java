package com.example.locstreamserver.repository;

import com.example.locstreamserver.databaseConnectionInfo;
import com.example.locstreamserver.model.Beacon;
import com.example.locstreamserver.model.Event;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;


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

    private static boolean isMoreRecent(Event newEvent, Event existingEvent) {
        // Directly compare the timestamp strings
        System.out.println("Comparing timestamps" + newEvent.timeStamp + " and " + existingEvent.timeStamp);
        return newEvent.timeStamp.compareTo(existingEvent.timeStamp) > 0;

    }


    public String processEventsForLocatorNode(int id, List<Event> events) {
        if((id==1)) {
            System.out.println("Received rquest from node 1");
            //list all events
            int eventsc = 0;
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                if(event!=null) {
                    System.out.println("event mac=" + event.getMacAddress().toLowerCase());
                    eventsc++;
                }

            }
            System.out.println("Received " + eventsc + " from node1");
        }

        if(id == 0) {
            return "Invalid locator attempting to push events.";

        } else {
            System.out.println("Processing events from device " + id + "...  ");
        }

        ResultSet result = null; //where to store the SQL results
        //System.out.println("Number of events: " + events.size());
        if(events.size() < 1) {
            System.out.println("No events reported, dropping request");
            return "No events reported, dropping request";
        }

        ArrayList<Integer> beaconIds = new ArrayList<>();
        //GET query the database for beacons in SQL that exist in the current reported list of events.
        //format: SELECT * FROM beacons WHERE macAddress IN ('value1', 'value2', 'value3', ...);
        //System.out.println("Events:"+ events.toString());
        String queryString = "SELECT beaconId, macAddress FROM beacons WHERE macAddress IN (";
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);

            if (event != null) {
                String temp = event.getMacAddress().toLowerCase();
                temp = temp.trim();
                //System.out.println("getMacAddress" + temp);
                queryString += "'" + temp + "'";

                // Add comma for all but the last event
                if (i < events.size() - 1) {
                    queryString += ",";
                }
            } else {
                return "Mac address was not sent.";
            }
        }
        queryString += ");";

//Compare results from the GET statement to the provided list of detected BLE. If a known device is seen, insert that into the database.
        //System.out.println("Query string about to be executed : " + queryString);
        try {
            //System.out.println("Query string executed : "+ queryString);
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);
            //System.out.println("Query String: " + queryString);

            //compare the results fromt he GET statement to the provided list of detected BLE nodes. If a known device is seen, insert that into the database.
            int count=0;//Count how many beacons were

            if(result != null) {

                queryString = "INSERT INTO BeaconEvents(locatorId, beaconId, signalStrength, detectTime) VALUES";
                while (result.next()) {  //each result is a match for something reported.
                    //System.out.println("Processing events for mac address: " + result.getString("macAddress"));
                    // if the event
                    int beaconId = result.getInt("beaconId");
                    String macAddress = result.getString("macAddress");
                    beaconIds.add(beaconId);

                    //what to do for each confirmed relevent value in "events"
                    for (Event event : events) {
                        //System.out.println("Evaluating event for mac address " + event.getMacAddress() + " and comparing it to " + macAddress);
                        if (event.getMacAddress() != "" && event.getMacAddress().equals(macAddress)) {
                            //System.out.println("Matching beacon mac address seen: " + macAddress);

                            event.setBeaconId(beaconId);
                            event.setLocatorId(id);
                            //System.out.println("Event data: " + event.getDBInsertString());

                            String temp = event.getDBInsertString(); //queryString for inserting records

                            queryString += temp;
                            queryString += ",";
                            //System.out.println("Database insert string: " + queryString);
                        }
                    }
                    count++;
                }
                if (count > 0) {
                    queryString = queryString.substring(0, queryString.length() - 1);
                    queryString += ";"; // Close the query
                    // Execute the query here with stmt.executeUpdate(queryString)
                    try {

                        connection.createStatement();
                        stmt.executeUpdate(queryString);
                    } catch (SQLException a) {
                        a.printStackTrace();
                        System.out.println("Error occured when inserting new rows to beaconevents table. Query: " + queryString);

                    }



                } else {
                    return "No valid beacons to add to events table.";
                }
            }

            connection.close();
            //System.out.println("Events have been reported for " + count + " valid beacons from LocatorNode " + id + ". Adding events to database...");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);
            return "Adding events FAILED. Query string was:  " + queryString;
        }


        //Ownership checks on devices that were found
        try { //perform insert into database here

            //System.out.println("Sending to evaluateOwnership process...");
            for (int i = 0; i < beaconIds.size(); i++) {
                Integer beaconId = beaconIds.get(i);
                // Use beaconId here
                evaluateOwnership(beaconId, id);
            }
            int numberOfEvents = events.size()-1;
            int numValid = beaconIds.size();
            //System.out.println("Request has been received from locatorId " + id + " to process events. Number of events reported: " + numberOfEvents + "  of which there were " + numValid + " valid beacons.");



            return "Events processed successfully. ";
        } catch (Exception e) {
            //System.out.println("SQL Error in addEvents(): ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);
            return "Ownership checks FAILED. Query string was:  " + queryString;
        }

        //Insert logic here to update owners for detected nodes if necessary.
        //evaluateOwnership();
        //try { //perform insert into database here
        //    evaluateOwnership(event.getBeaconId(), id);
        //} catch (Exception  e) {
        //    System.out.println("Error occurred while evaluating ownership. ");
        //    return "An error occurred while evaluating ownership.";
       // }

    }



    public void evaluateOwnership(int beaconId, int newLocatorNodeId) {
        //In order to evaluate ownership, we'll need: beaconId, newLocatorNodeId, ownerLocatorNodeId
        int currentOwnerId;
        //System.out.print("Checking ownership for beaconId " + beaconId + ".   ");

        BeaconRepository beacon = new BeaconRepository();

        //get current owner from SQL database using beaconId:
        currentOwnerId = beacon.getCurrentOwnerById(beaconId);

        //do not proceed if this node already is the owner
        if (currentOwnerId == newLocatorNodeId){
            //System.out.println("Owner is already set correctly.");
            return;
        } else if (currentOwnerId == -1) {
            System.out.println("Current owner is null. Setting owner.");
            BeaconRepository.logOwnershipChange(beaconId, newLocatorNodeId);
            BeaconRepository.setOwner(newLocatorNodeId, beaconId);
        }

        //get last 100 records for this beaconId
        ArrayList<Event> events = getLast100EventsById(beaconId);

        //Create a hashset of Locator Nodes seen in recent events for this beacon
        Set<Integer> uniqueBeaconIds = new HashSet<>();
        for (Event event : events) {
            uniqueBeaconIds.add(event.getLocatorId());
        }

        //If there is only one locator node in recent times that has seen this locator, set the owner.
        //System.out.println("Size of uniquebeaconIds is: " + uniqueBeaconIds.size());
        if (uniqueBeaconIds.size() == 1 ) {
            Integer storedLocatorId = uniqueBeaconIds.iterator().next();
            //if(storedLocatorId != newLocatorNodeId) {
            BeaconRepository.logOwnershipChange(beaconId, newLocatorNodeId);
            BeaconRepository.setOwner(newLocatorNodeId, beaconId);
                System.out.println("Only one Locator is reporting events for this beacon, and it is not this one. Setting owner to " + newLocatorNodeId);
                return;
            //}
        }

        //store total signal strength and count for each beacon
        int originalTotalSignal = 0;
        int originalTotalCount = 0;
        int newTotalSignal = 0;
        int newTotalCount = 0;

        for(Event event : events) {
            if(event.getLocatorId() == newLocatorNodeId) {
                if(event.getSignalStrength() > -80) {
                    newTotalSignal += event.getSignalStrength();
                    newTotalCount++;
                }
            } else if (event.getLocatorId() == currentOwnerId) {
                if(event.getSignalStrength() > -80) {
                    originalTotalSignal += event.getSignalStrength();
                    originalTotalCount++;
                }
            }
        }
        /*
        Map<Integer, Integer[]> totals = new HashMap<>();
        for(Event event : events) {
            totals.putIfAbsent(event.getBeaconId(), new Integer[]{0,0});
            totals.get(event.getBeaconId())[0] += event.getSignalStrength();
            totals.get(event.getBeaconId())[1]++;
            System.out.println("event.getBeaconId())[0] " + event.getBeaconId())[0]);
        }
        //map to store average signal strength for each
        Map<Integer, Double> averages = new HashMap<>(); //map to store averages
        for (Integer id : totals.keySet()) {
            Integer[] data = totals.get(id);
            double average = (double) data[0] / data[1];
            averages.put(id, average);
        }
        */

        Double oldOwnerAverage = (double) originalTotalSignal / originalTotalCount;
        Double currentLocatorAverage = (double) newTotalSignal / newTotalCount;
        //System.out.print("Original average: " + oldOwnerAverage + "   New average:  " + currentLocatorAverage + "   ");

        if(currentLocatorAverage > oldOwnerAverage+10 && newTotalCount>2) {
            System.out.println("Changing owner of beaconId " + beaconId + " due to higher average signal strength at node " + newLocatorNodeId + "CurrentAverage: " + currentLocatorAverage + "  oldOwnerAverage: "+ oldOwnerAverage);
            BeaconRepository.logOwnershipChange(beaconId, newLocatorNodeId);
            BeaconRepository.setOwner(newLocatorNodeId, beaconId);

            return;
        } else {
            //System.out.println("Current locator average: " + currentLocatorAverage + "   oldOwnerAverage:  " + oldOwnerAverage);
        }
        //System.out.println("Beacon seen by different locator, but ownership was not changed for " + beaconId);
    }

    public void setBeaconOwner(int newOwnerId, int beaconId) {
        System.out.println("EXECUTE CODE TO CHANGE OWNER!");

        String queryString = "UPDATE beacons SET currentOwner = " + newOwnerId + "WHERE beaconId = " + beaconId;

    }

    public String addEvents(List<Event> events) {
        String queryString = "INSERT INTO BeaconEvents(locatorId, beaconId, signalStrength, detectTime) VALUES";
        Event lastEvent = events.get(events.size() - 1);
        //System.out.println("Last Event: " + lastEvent);
        //System.out.println("Events reporting: " + events);

        //Now that the events have all be input, next step is to find out if the owner should change.
        //compareAndUpdateOwner(events);
                //	Insert into BeaconEvents(locatorId, beaconId, signalStrength, detectTime) values(1, 1, 54, CONVERT(DATETIME, '2022-05-08 14:30:45', 120));
        //Map<Integer, Event> mostRecentEvents = new HashMap<>();
        //String beaconIds = "";
        //add values to the string for events table, add beaconIDs and signalStrength
        for (Event event : events) {
            if (event != null) {
                String temp = event.getDBInsertString(); //queryString for inserting records
                queryString += temp;
                if (event != lastEvent && temp != "") {
                    queryString += ",";
                }
            }
            //System.out.println(event.toString());
        }
//        compareAndUpdateOwner(mostRecentEvents);

        queryString += ";";
        System.out.println("addEvents query string: " + queryString);

        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            //System.out.println("Connected to Microsoft SQL Server. (addEvents())");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(queryString);
            //System.out.println("Query String: " + queryString);
            connection.close();
            return "Events added successfully.";
        } catch (SQLException e) {
            //System.out.println("SQL Error in addEvents(): ");
            e.printStackTrace();
            System.out.println("Query failed to execute: " + queryString);
            return "Adding events FAILED. Query string was:  " + queryString;
        }




    }

    private void compareAndUpdateOwner(List<Event> events) {
/*
    This query will return the most recent result for a specific beaconId for every locator that has seen it.
    */
        for (Event event : events) {
            int id = event.getBeaconId();



    String queryString = """
	SELECT
    BE.eventId,
    BE.locatorId,
    BE.detectTime,
    BE.SignalStrength, 
     BE.beaconId-- Include other columns you need from BeaconEvents
FROM
    BeaconEvents BE
INNER JOIN
    (SELECT
         locatorId,
         MAX(detectTime) as LatestDetectTime
     FROM
         BeaconEvents
     WHERE
         beaconId = '""" + id + "'" + """ 
     GROUP BY
         locatorId) AS LatestDetects
ON
    BE.locatorId = LatestDetects.locatorId
    AND BE.detectTime = LatestDetects.LatestDetectTime
WHERE
    BE.beaconId = '""" + id + "'  ORDER BY detectTime desc;";   //Later come back and add AND timestamp > CURRENT_TIMESTAMP - INTERVAL '5 minutes' to the where clause

    //System.out.println("compareAndUpdateOwner Query: " + queryString);
        ResultSet result = null; //where to store the SQL results
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            //System.out.println("Connected to Microsoft SQL Server. (compareAndUpdateOwner())");
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);
            //System.out.println("result = " + result);
            //System.out.println("Query String: " + queryString);


            if(result != null) {


                while (result.next()) {  //result contains the most recent of each locator node that has seen this beacon ID. If current node's signalSTrength > the old signalStrength || current node's timestamp is more than 3 minutes newer, change owner
                    Event e = new Event();
                    e.setEventId(result.getInt("eventId"));
                    e.setLocatorId(result.getInt("locatorId"));
                    e.setBeaconId(result.getInt("beaconId"));
                    e.setSignalStrength(result.getInt("signalStrength"));
                    e.setTimeStamp(result.getString("detectTime"));
                    System.out.println(e);

                    System.out.println("event.getSignalStrength = " + event.getSignalStrength());
                    System.out.println("e.getSignalStrength = " + e.getSignalStrength());


                    if(event.getSignalStrength() > (1.1 * e.getSignalStrength())       ){
                        System.out.println("Signal Strength Increased!");
                        BeaconRepository.setOwner(event.getLocatorId(), event.getBeaconId());
                    } else {
                        System.out.println("signalStrengthDidNotTrigger");
                    }


                  }
            }
            connection.close();

        } catch (SQLException e) {
            System.out.println("SQL Error in compareAndUpdateOwner(): "+"Query failed to execute: " + queryString);
            e.printStackTrace();
        }
        }

    /*
    If number of results = 1 then set the owner to this report
    If the number of results >1 && the newLocatorId != CurrentOwner,
        If newLocatorId signalstrength > CurrentOwner signalstrength * 1.15
           Set new owner

 */


        //build query
        //String query = "Select beaconId, signalStrength where beaconId IN (";
        //for (Map.Entry<Integer, Event> entry : mostRecentEvents.entrySet()) {
          //  Integer key = entry.getKey();




            //compare value
            //Event value = entry.getValue();
        //}




    }

    public ArrayList<Event> getLast100EventsById(int beaconId) {
        ArrayList<Event> events = new ArrayList<Event>();
        //SELECT TOP 100 * FROM (tablevar) ORDER BY ID DESC;
        ResultSet result = null; //where to store the SQL results
        //ArrayList<Beacon> arr = new ArrayList<>(); //where to store the results prior to returning them
        //build sql string
        String time = Instant.now().minusSeconds(8).toString();
        String queryString = "SELECT TOP 100 * FROM " + eventsTable + " WHERE beaconId = '" + beaconId + "' AND detectTime >= '" + time + "'" + " ORDER BY detectTime DESC";

        //returns last 100 events
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            //System.out.println("Connected to Microsoft SQL Server. (getLast100EventsById())");
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);
            //System.out.println("result = " + result);
            //System.out.println("Query String: " + queryString);

            while (result.next()) {
                Event event = new Event();
                event.setEventId(result.getInt("eventId"));
                event.setLocatorId(result.getInt("locatorId"));
                event.setBeaconId(result.getInt("beaconId"));
                event.setSignalStrength(result.getInt("signalStrength"));
                event.setTimeStamp(result.getString("detectTime"));
                //System.out.println(event.toString());
                events.add(event);
                //System.out.println(event.toString());
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
            //System.out.println("Connected to Microsoft SQL Server. (getLast100Events())");
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(queryString);
            //System.out.println("result = " + result);
            //System.out.println("Query String: " + queryString);



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

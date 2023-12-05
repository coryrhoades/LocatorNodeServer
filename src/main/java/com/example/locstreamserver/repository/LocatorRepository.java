package com.example.locstreamserver.repository;

import com.example.locstreamserver.model.Event;
import com.example.locstreamserver.model.LocatorNode;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;
import com.example.locstreamserver.databaseConnectionInfo;

import static com.example.locstreamserver.repository.locatorDatabaseQueries.databaseExecuteQuery;
import static com.example.locstreamserver.repository.locatorDatabaseQueries.databaseGetQueryResult;

@Component
public class LocatorRepository {
    //Bring in the database connection library
    locatorDatabaseQueries locatorDatabase = new locatorDatabaseQueries();
    List<LocatorNode> locators = new ArrayList<>();
    Random random = new Random(); //temporary variable as well

    public LocatorRepository() {
        //adding two  temporary records for testing purposes. Populate from SQL Database here ??
        //int locatorId, String locatorName, int associatedCameraId, int accountId, String locatorMAC) {
        //locators.add(new LocatorNode(1,"FirstTest",1,1, macAddress() ));
        //locators.add(new LocatorNode(2,"SecondTest",2,1, macAddress() ));
        //locators.add(new LocatorNode(3,"ThirdTest",1,2, macAddress() ));
        //locators.add(new LocatorNode(4,"FourthTest",2,2, macAddress() ));
        //above is initial testing code prior to having a database

    }

    public List<LocatorNode> GetAllLocators(String apiKey, String secretKey) { //(@RequestHeader("API-Key") String apiKey,@RequestHeader("Secret-Key") String secretKey)
        // Authentication and logic to return an array of LocatorNode objects

        return locators; // Replace with actual implementation
    }
    public List<LocatorNode>  GetLocatorsByAccount(String apiKey, String secretKey, int accountId) { //(@RequestHeader("API-Key") String apiKey,@RequestHeader("Secret-Key") String secretKey)
        // Authentication and logic to return an array of LocatorNode objects
        List<LocatorNode> filteredLocators = new ArrayList<>();

        // Loop through all locators and add the ones with matching accountId to filteredLocators
        for (LocatorNode locator : locators) {
            if (locator.getAccountId() == accountId) {
                filteredLocators.add(locator);
            }
        }

        return filteredLocators; // Replace with actual implementation
    }

    public String AddLocator(String locatorName, String macAddress ) {
        //Instant.now().toString();
        //locators.add(new LocatorNode(locatorId,locatorName,associatedCameraId,accountId, macAddress() )); OLD
        //come back later and verify that there is not already a locator with the same mac address under this account ID first.
        //perform initial query check and return "Locator already exists" for example.
        //LocatorCountByAccount(1);


        //build database query
        String query = "INSERT INTO LocatorNodes (locatorName, locatorMAC, dateAdded) VALUES('" + locatorName + "', '" + macAddress + "', '" + Instant.now().toString() + "'" + ")";
        System.out.println("Query = " + query);

        //Execute
        locatorDatabase.databaseExecuteQuery(query);

        //establish a database connection

        return "Locator added.";
    }

        public int LocatorCountByAccount(int accountId) {
            //ex query: select COUNT(locatorId) AS NumberOfLocatorNodes from LocatorNodes Where accountId = 1
            String query = "select (COUNT(locatorId) AS NumberOfLocatorNodes from LocatorNodes Where accountId = " + accountId;
            ResultSet result = databaseGetQueryResult(query);

            return 1;

        }



    //temporary random mac address generator
    public String macAddress() {
        Random random = new Random();

        // Generate a random MAC address
        String macAddress = String.format("02:%02x:%02x:%02x:%02x:%02x:%02x",
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256));
        return macAddress;
    }

    public int GetLocatorByMac(String apiKey, String secretKey, String id) {
        int myId= 0;

        //query database for this mac address
        String query = "SELECT * from LocatorNodes where locatorMac = '" + id + "'";
        //ResultSet result = databaseGetQueryResult(query);
        //System.out.println("Query is: " + query);

        ResultSet result = null; //where to store the SQL results
        try {
            databaseConnectionInfo sqlserver = new databaseConnectionInfo();
            Connection connection = DriverManager.getConnection(sqlserver.getUrl(), sqlserver.getDbuser(), sqlserver.getDbpass());
            //System.out.println("Connected to Microsoft SQL Server. (compareAndUpdateOwner())");
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(query);
            //System.out.println("result = " + result);
            //System.out.println("Query String: " + queryString);


            if(result != null) {


                while (result.next()) {  //result contains the most recent of each locator node that has seen this beacon ID. If current node's signalSTrength > the old signalStrength || current node's timestamp is more than 3 minutes newer, change owner
                    LocatorNode L = new LocatorNode();
                    L.setLocatorMAC(result.getString("locatorMAC"));
                    L.setLocatorId(result.getInt("locatorId"));

                    System.out.println("L MAC: " + L.getLocatorMAC() + "    ID to match: " + id);

                    if (L.getLocatorMAC().equals(id)) {
                        System.out.println("Locator MAC matched with locator ID: " + L.getLocatorId());
                        return L.getLocatorId();
                    }

                }
            }
            connection.close();

        } catch (SQLException e) {
            System.out.println("SQL Error in compareAndUpdateOwner(): "+"Query failed to execute: " + query);
            e.printStackTrace();
        }








        System.out.println("Query = " + query);


        System.out.println("GetLocationByMac: " + id);
        for (LocatorNode locator : locators) {
            if (locator.getLocatorMAC() == id) {
                myId = locator.getLocatorId();
                System.out.println("Locator Node detected.");
            }

        }


        return myId;
    }


}

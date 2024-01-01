package com.example.locstreamserver.repository;

import com.example.locstreamserver.databaseConnectionInfo;
import com.example.locstreamserver.model.Event;
import com.example.locstreamserver.model.mmWaveEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

public class mmWaveRepository {



    public String addEvents(List<mmWaveEvent> events) {
        String queryString = "INSERT INTO mmWaveEvents(startTime, endTime, macAddress) VALUES ";
        mmWaveEvent lastEvent = events.get(events.size() - 1);

        for (mmWaveEvent event : events) {
            if (event != null) {
                String temp = event.getDBInsertString(); //queryString for inserting records
                queryString += temp;
                if (event != lastEvent && temp != "") {
                    queryString += ",";
                }
            }
        }

        queryString += ";";
        System.out.println("mmWave addEvents query string: " + queryString);

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


            //return "";
    }

}

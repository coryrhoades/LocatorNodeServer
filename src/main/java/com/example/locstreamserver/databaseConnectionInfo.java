package com.example.locstreamserver;

public class databaseConnectionInfo
{
    static String serverIP = "192.168.50.243";
    static String url; //= "jdbc:sqlserver://192.168.50.243;databaseName=LocatorStreamServer;trustServerCertificate=true;";
    static String dbuser = "sa";
    static String dbpass = "C@llth3h0ff";
    static String databaseName = "LocatorStreamServer";

//    url = database.getURL();
//    dbuser = database.getUser();
//    dbpass = database.getPass();
//    table = database.getTable();


    public databaseConnectionInfo(){
        url = "jdbc:sqlserver://" + serverIP + ";databaseName=" + databaseName + ";trustServerCertificate=true;QueryTimeout=2";
        System.out.println("SQL Server Connection URL: " + url);
    }

    public static String getDbpass() {
        return dbpass;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static String getUrl() {
        return url;
    }

    public static String getDbuser() {
        return dbuser;
    }

}

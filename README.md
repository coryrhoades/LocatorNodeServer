# LocatorNodeServer
This project is the serverside component for implementing BLE beacon locator nodes and pushing events into video management servers when the beacons come closer to a new node.

The locator nodes will push commands to the API that will do the following:

-Add a new locator to the database.

-Get a list of all locators in the database.

-Report all BLE MAC addresses and signal strength (for use from the Locator Nodes / BLE Listeners)

-Get which node a specified locator was last seen at.

-Get all occurrances of locator changes in date range.






API Documentation

Beacon API:

POST /beacons/add
Send mac address, optional friendly name. Will be inserted into SQL database. Response will return JSON with full beacon info including beaconId.
{
    "mac": "abcdefy",
    "name": "test"
}

GET /beacons
Returns all beacons within database in JSON format:
    {
        "beaconId": 101,
        "macAddress": "abcdefg",
        "beaconName": "TestBeacon2",
        "currentOwner": null
    }

GET /beacons/{id}
Returns beacon object with this ID in JSON format:
ex: localhost:8080/beacons/101
    {
        "beaconId": 101,
        "macAddress": "abcdefg",
        "beaconName": "TestBeacon2",
        "currentOwner": null
    }
    

POST /beacons
Returns all objects with mac address specified as "mac" within JSON info sent. Send:
{
    "mac": "abcdefg"
}

Returns: 

    {
        "beaconId": 100,
        "macAddress": "abcdefg",
        "beaconName": "TestBeacon1",
        "currentOwner": null
    }




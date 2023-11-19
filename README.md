# LocatorNodeServer
This project is the serverside component for implementing BLE beacon locator nodes and pushing events into video management servers when the beacons come closer to a new node.

The locator nodes will push commands to the API that will do the following:

-Add a new locator to the database.

-Get a list of all locators in the database.

-Report all BLE MAC addresses and signal strength (for use from the Locator Nodes / BLE Listeners)

-Get which node a specified locator was last seen at.

-Get all occurrances of locator changes in date range.




document.addEventListener("DOMContentLoaded", function() {
    fetch('../sidebar.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('sidebar-container').innerHTML = data;

            // Now that the sidebar is loaded, attach event listeners to the buttons
            setupSidebarEventListeners();

            // Also load the "View Locators" content by default
            fetchAndDisplayBeacons();
        })
        .catch(error => console.error('Error loading the sidebar:', error));
});

function setupSidebarEventListeners() {
    var viewLocatorsBtn = document.getElementById('viewLocators');
    if (viewLocatorsBtn) {
        viewLocatorsBtn.addEventListener('click', fetchAndDisplayBeacons);
    }

    var addLocatorsBtn = document.getElementById('addLocators');
    if (addLocatorsBtn) {
        addLocatorsBtn.addEventListener('click', displayAddLocatorForm);
    }

    var addBeaconsBtn = document.getElementById("addBeacons");
    if(addBeaconsBtn) {
        addBeaconsBtn.addEventListener('click', displayAddBeaconForm);
    }
}


function displayAddLocatorForm() {
    const contentArea = document.getElementById('content-area');
    contentArea.innerHTML = `
        <H1>Add Locator</H1>
        <form id="addLocatorForm"> 
            <label for="macAddress">MAC Address:</label>
            <input type="text" id="macAddress" name="locatorMAC" required><br>
            <label for="locatorName">Name (optional):</label>
            <input type="text" id="locatorName" name="locatorName"><br>
            <button type="submit">Submit</button>
        </form>
    `;

    // Attach event listener to the form
    document.getElementById('addLocatorForm').addEventListener('submit', function(event) {
        event.preventDefault();
        submitLocatorForm();
    });
}

function submitLocatorForm() {
    const macAddress = document.getElementById('macAddress').value;
    const locatorName = document.getElementById('locatorName').value;

    const data = {
        locatorMAC: macAddress,
        locatorName: locatorName
    };

    fetch('http://localhost:8080/locators', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            // Handle success - maybe clear form or display a success message
        })
        .catch((error) => {
            console.error('Error:', error);
            // Handle errors - display error message to user
        });
}

function displayAddBeaconForm() {
    const contentArea = document.getElementById('content-area');
    contentArea.innerHTML = `
        <H1>Add Beacon</H1>
        <form id="addBeaconForm"> 
            <label for="macAddress">MAC Address:</label>
            <input type="text" id="macAddress" name="locatorMAC" required><br>
            <label for="beaconName">Name (optional):</label>
            <input type="text" id="beaconName" name="beaconName"><br>
            <button type="submit">Submit</button>
            <div id="formResponse" style="color: green; margin-top: 10px;"></div> <!-- Response message will be shown here -->

        </form>
    `;

    // Attach event listener to the form
    document.getElementById('addBeaconForm').addEventListener('submit', function(event) {
        event.preventDefault();
        submitBeaconForm();
    });
}

function submitBeaconForm() {
    const macAddress = document.getElementById('macAddress').value;
    const beaconName = document.getElementById('beaconName').value;

    const data = {
        mac: macAddress,
        name: beaconName
    };

    fetch('http://localhost:8080/beacons/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const responseElement = document.getElementById('formResponse');
            if (data.length === 0) {
                // Update the formResponse to say "Failed"
                responseElement.innerText = "Beacon Add Failed";
            } else {
                console.log('Success:', data);
                document.getElementById('addBeaconForm').reset();
                responseElement.innerText = "Beacon Added";

                setTimeout(() => {
                    responseElement.innerText = '';
                }, 5000);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            // Handle errors - display error message to user
        });
}



function fetchAndDisplayBeacons() {
    console.log("Attempting fetchAndDisplayBeacons()");
    fetch('http://localhost:8080/beacons')
        .then(response => response.json())
        .then(data => {
            const tableContainer = document.getElementById('content-area');
            const table = createTable(data);
            tableContainer.innerHTML = ''; // Clear existing content
            tableContainer.appendChild(table); // Add the new table
        })
        .catch(error => console.error('Error fetching beacons:', error));
}

function createTable(data) {
    const table = document.createElement('table');
    table.className = 'beacons-table';

    // Create table header
    const thead = table.createTHead();
    const headerRow = thead.insertRow();
    const headers = ['Beacon ID', 'Beacon Name', 'MAC Address', 'Current Owner'];
    headers.forEach(text => {
        const th = document.createElement('th');
        th.textContent = text;
        headerRow.appendChild(th);
    });

    // Create table body
    const tbody = table.createTBody();
    data.forEach(item => {
        const row = tbody.insertRow();
        row.insertCell().textContent = item.beaconId;
        row.insertCell().textContent = item.beaconName;
        row.insertCell().textContent = item.macAddress;
        row.insertCell().textContent = item.currentOwner || 'N/A'; // Display 'N/A' if null
    });

    return table;
}





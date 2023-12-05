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

    fetch('../style.css')
        .then(response => response.text())
        .then(css => {
            const style = document.createElement('style');
            style.textContent = css;
            document.head.appendChild(style);
        })
        .catch(error => console.error('Error loading CSS:', error));
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



function fetchAndDisplayOwnerLogs() {
    console.log("Attempting fetchAndDisplayBeacons()");
    fetch('http://localhost:8080/events/ownerLogs')
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
    const headers = ['Beacon ID', 'Timestamp', 'Previous Owner', 'New Owner'];
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
        row.insertCell().textContent = item.timestamp;
        row.insertCell().textContent = item.previousOwner;
        row.insertCell().textContent = item.newOwner ;
    });

    return table;
}

fetchAndDisplayOwnerLogs(); // Call initially

setInterval(fetchAndDisplayOwnerLogs, 5000); // Refresh every 5 seconds

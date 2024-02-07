const tkn=localStorage.getItem("token");
function getData() {
    const url="http://localhost:8080/User/getCustomers";
    
    fetch(url,{
        method:"GET",
        headers:{
            'Authorization':`Bearer ${tkn}`,
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Not able to hit the API")
            } else {
                return response.json();
            }
        }).then(data => {

            generateTableRows(data);
        })
        .catch(error => console.error("Error Fetching data", error));
}

function generateTableRows(data) {
    const tbody = document.getElementById("customerData");
    tbody.innerHTML = ""; // Clear existing rows
    data.forEach(customer => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${customer.firstName}</td>
            <td>${customer.lastName}</td>
            <td>${customer.address}</td>
            <td>${customer.city}</td>
            <td>${customer.state}</td>
            <td>${customer.email}</td>
            <td>${customer.phone}</td>
            <td class="action-buttons">
                <button onclick="deleteCustomer('${customer.email}')">Delete</button>
                <button onclick="updateCustomer(this.parentElement.parentElement, '${customer.email}', '${customer.firstName}', 
                '${customer.lastName}', '${customer.phone}', '${customer.state}', '${customer.address}',  '${customer.city}')">Update</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}
function deleteCustomer(email) {
   
    fetch(`http://localhost:8080/User/deleteCustomer?email=${email}`, {
        method: `DELETE`,
        headers:{
            'Authorization':`Bearer ${tkn}`,
        }
    })
        .then(response => {
            if (response.ok) {
                const row = document.querySelector(`tr[data-email="${email}"]`);
                if (row) {
                    row.remove();
                }
                return response.text();
            } else if (response.status === 404) {
                return response.text();
            } else {
                throw new Error("Failed to hit API")
            }
        })
        .then(data => {
            console.log(data);
            getData();
        })
        .catch(error => {
            console.error(error.message);
        })
}

function updateCustomer(row, email, firstName, lastName, phone, state, address, city) {
    console.log("i am at start updatefun");
    row.innerHTML = `
        <td><input type="text" value="${firstName}" class="firstName"></td>
        <td><input type="text" value="${lastName}" class="lastName"></td>
        <td><input type="text" value="${address}" class="address" ></td>
        <td><input type="text" value="${city}" class="city"></td>
        <td><input type="text" value="${state}" class="state"></td>
        <td><input type="text" value="${email}" class="email"></td>
        <td><input type="text" value="${phone}" class="phone"></td>
        <td class="action-buttons">
            <button onclick="confirmUpdate(this, '${email}')">Confirm</button>
            <button onclick="cancelUpdate(this,'${email}', '${firstName}', 
            '${lastName}', '${phone}', '${state}', '${address}',  '${city}')">Cancel</button>
        </td>
    `;
    console.log('i am at end of updatefun');
}

function valueChange(e){
    console.log(e.target);
}

function confirmUpdate(button, email) {

    const row = button.parentElement.parentElement;
    const requestData = {};
    console.log(row);
    const firstName = row.querySelector('.firstName').value;
    const lastName = row.querySelector('.lastName').value;
    const phone = row.querySelector('.phone').value;
    const state = row.querySelector('.state').value;
    const address = row.querySelector('.address').value;
    const city = row.querySelector('.city').value;
    requestData["firstName"] = firstName;
         
        requestData["lastName"]= lastName.toUpperCase();
        requestData["phone"]=phone;
       requestData["state"] =state.toUpperCase();
        requestData["address"]=address.toUpperCase();
        requestData["city"]=city.toUpperCase();
        requestData["email"]=email;
     console.log(requestData);

    fetch(`http://localhost:8080/User/updateCustomer?email=${email}`, {
        method: "PUT",
      
        headers:{
            'Authorization':`Bearer ${tkn}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)

    }).then(response => {
        if (response.ok) {
            return response.json();
        } else if (response.status === 404) {
            return response.text();
        } else {
            throw new Error(`Error:${response.status}-${response.statusText}`);

        }
    }).then(data => {
            console.log(data);
            getData();
        })
        .catch(error => {
            console.error('Error:', error.message);
        })
}

function cancelUpdate(button, email, firstName, lastName, phone, state, address, city) {
    // Get the row element
    const row = button.parentElement.parentElement;

    // Restore the original row content
    row.innerHTML = `
    <td>${firstName}</td>
    <td>${lastName}</td>
    <td>${address}</td>
    <td>${city}</td>
    <td>${state}</td>
    <td>${email}</td>
    <td>${phone}</td>
    <td class="action-buttons">
        <button onclick="deleteCustomer('${email}')">Delete</button>
        <button onclick="updateCustomer(this.parentElement.parentElement, '${email}', '${firstName}', 
        '${lastName}', '${phone}', '${state}', '${address}',  '${city}')">Update</button>
    </td>
    `;
}

var selectedValue;

document.getElementById('search_by').addEventListener('change', function() {
  selectedValue = this.value;
  console.log('Selected value:', selectedValue);
});

function search(){
    var searchBy=document.getElementById('search_by').value;
    var item=searchBy=="email"?document.querySelector('.search_field').value:document.querySelector('.search_field').value.toUpperCase();
      console.log(item);
    fetch(`http://localhost:8080/User/searchBy?key=${searchBy}&value=${item}`,{
        method:"GET",
        headers:{
            'Authorization':`Bearer ${tkn}`,
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Not able to hit the API")
        } else {
            return response.json();
        }
    }).then(data => {
        console.log('list=>', data)
        generateTableRows(data);
    })
    .catch(error => console.error("Error Fetching data", error));
}

window.onload = function() {
    getData();
  }
function addCustomer(){
    window.location.href = "addCustomer.html";
}

/*function callApi(){
    const apiUrl = 'https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp';
const authorizationHeader = `Bearer ${localStorage.getItem("token")}`;
const cmdParameter = 'get_customer_list';

// Construct URL with parameters
const url = new URL(apiUrl);
url.searchParams.append('cmd', cmdParameter);

fetch(url, {
    method: 'GET',
    headers: {
        'Authorization': authorizationHeader
    }
})
.then(response => {
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
})
.then(data => {
    // Process the JSON response
    console.log(data);
})
.catch(error => {
    console.error('Error:', error);
});
}*/
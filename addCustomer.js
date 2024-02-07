const tkn=localStorage.getItem("token");

document.getElementById("customerForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form submission from reloading the page
    const h5=document.getElementById('giveMsg');
    // Get form data
    const formData = new FormData(event.target);
    event.target.reset();
    const requestData = {};
    formData.forEach((value, key) => {
        requestData[key] = key=="email"?value:value.toUpperCase();
    });
     
    // Make POST request to API endpoint
    fetch("http://localhost:8080/User/createCustomer", {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${tkn}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
    })
        .then(response => {
            if (response.ok) {
                h5.style.color = "green";
                return response.text();
            } else if (response.status === 409|| response.status===401) {
                h5.style.color = "red";
                return response.text();
            } else {
                console.log(tkn);
                throw new Error(`Error:${response.status}-${response.statusText}`);

            }

        })
        .then(data => {
            h5.textContent=data;
            if(h5.style.color=="green"){
                window.location.href = "customerList.html";
            }           
           
        })
        .catch(error => {
           
            console.error('Error:', error.message);
        })
     
});



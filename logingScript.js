
document.getElementById("customerForm").addEventListener("submit", function (event) {
    event.preventDefault(); 
    const h5=document.getElementById('giveMsg');
    

    const formData = new FormData(event.target);
    event.target.reset();
    const requestData = {};
    formData.forEach((value, key) => {
        requestData[key] = key=="email"?value:value;
    });
    
      
    fetch("http://localhost:8080/User/login",{
        method:"POST",      
        headers:{
            
            "Content-Type": "application/json"
        },
        body : JSON.stringify(requestData)
    }).then(response=>{
        if (!response.ok) {
            throw new Error("Login failed. Please check your credentials");
        }
        //console.log(response);
        return response.json(); })
    .then(data=>{
    
        localStorage.setItem("token",data.jwtToken);
        window.location.href = "customerList.html";
       
    }).catch(error=>{
        console.error("Error:",error.message);
        alert("Loging failed. Please check your credentials");
    })
}  )
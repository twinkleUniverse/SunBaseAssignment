package com.example.SunBase.controller;

import com.example.SunBase.dtos.*;
import com.example.SunBase.exception.CustomerAlreadyExist;
import com.example.SunBase.exception.CustomerNotFound;
import com.example.SunBase.model.Customer;
import com.example.SunBase.service.CustomerService;
import com.example.SunBase.security.JwtService;
import com.example.SunBase.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService helper;
    @Autowired
    private AuthenticationManager manager;

    private Logger logger = LoggerFactory.getLogger(UserController.class);



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest request) {

       String str= this.doAuthenticate(request.getEmail(), request.getPassword());

       if(!str.equals("successful")) return new ResponseEntity<>(str,HttpStatus.CONFLICT);
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        System.out.println(userDetails);
        String token = this.helper.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            Authentication mng= manager.authenticate(authentication);
               return "successful";

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }


    // method to get list of all customers
    @GetMapping("/getCustomers")
    public ResponseEntity getCustomers(){
        List<CustomerResponseDto>CustomerList= customerService.getAllCustomer();
        return new ResponseEntity<>(CustomerList,HttpStatus.OK);
    }


    // method to get a customer of given id
    @GetMapping("/getById")
    public ResponseEntity getCustomerById(@RequestParam("id")int id) throws CustomerNotFound {
        try {
            CustomerResponseDto customerResponseDto = customerService.getById(id);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.OK);
        }catch (CustomerNotFound c){
            return new ResponseEntity<>(c.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    //Method to get the customers list of with specific value
    @GetMapping("/searchBy")
    public ResponseEntity getCustomerById(@RequestParam("key")String key, @RequestParam("value")String value) {
            List<CustomerResponseDto> customerResponseDtoList = customerService.searchBy(key,value);
            return new ResponseEntity<>(customerResponseDtoList, HttpStatus.OK);
    }
    // method to add a new customer
    @PostMapping("/createCustomer")
    public ResponseEntity addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExist {
        try {
            CustomerResponseDto customer1 = customerService.addCustomer(customer);
            return new ResponseEntity<>("Added Successfully",HttpStatus.OK);
        }catch (CustomerAlreadyExist e){
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);

        }
    }

    //update the customer
    @PutMapping("/updateCustomer")
    public ResponseEntity updateCustomer(@RequestBody CustomerRequestDto customerRequestDto, @RequestParam("email") String email)
        throws CustomerNotFound{
        try {
            CustomerResponseDto customerResponseDto = customerService.updateCustomer(customerRequestDto, email);
            return new ResponseEntity(customerResponseDto,HttpStatus.OK);
        }catch (CustomerNotFound c){
            return new ResponseEntity<>(c.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    //Method to delete the customer
    @DeleteMapping("/deleteCustomer")
    public ResponseEntity deleteCustomer(@RequestParam("email") String email)throws CustomerNotFound{
        try{
            String msg=customerService.delete(email);
            return new ResponseEntity<>(msg,HttpStatus.OK);
        }catch (CustomerNotFound c){
            return new ResponseEntity<>(c.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

   /* @PostMapping("/upadate")
    public ResponseEntity putSyncData(@RequestBody TestRequestDto testRequestDto){
       String str= customerService.upadateSync(testRequestDto);
                    return new ResponseEntity(str,HttpStatus.OK);

    }*/

}

package com.example.SunBase.controller;

import com.example.SunBase.dtos.CustomerRequestDto;
import com.example.SunBase.dtos.CustomerResponseDto;
import com.example.SunBase.exception.CustomerAlreadyExist;
import com.example.SunBase.exception.CustomerNotFound;
import com.example.SunBase.model.Customer;
import com.example.SunBase.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Customer")
public class CustomerController {
    final CustomerService customerService;
    @Autowired
    private CustomerController(CustomerService customerService){
        this.customerService = customerService;
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
            Customer customer1 = customerService.addCustomer(customer);
            return new ResponseEntity<>(customer1,HttpStatus.CREATED);
        }catch (CustomerAlreadyExist e){
            return new ResponseEntity(e.getMessage(),HttpStatus.OK);

        }
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity updateCustomer(@RequestBody CustomerRequestDto customerRequestDto, @RequestParam("id") int id)
        throws CustomerNotFound{
        try {
            CustomerResponseDto customerResponseDto = customerService.updateCustomer(customerRequestDto, id);
            return new ResponseEntity(customerResponseDto,HttpStatus.OK);
        }catch (CustomerNotFound c){
            return new ResponseEntity<>(c.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    //Method to delete the customer
    @DeleteMapping("/deleteCustomer")
    public ResponseEntity deleteCustomer(@RequestParam("id") int id)throws CustomerNotFound{
        try{
            String msg=customerService.delete(id);
            return new ResponseEntity<>(msg,HttpStatus.OK);
        }catch (CustomerNotFound c){
            return new ResponseEntity<>(c.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}

package com.example.SunBase.service;

import com.example.SunBase.dtos.CustomerRequestDto;
import com.example.SunBase.dtos.CustomerResponseDto;
import com.example.SunBase.dtos.TestRequestDto;
import com.example.SunBase.exception.CustomerAlreadyExist;
import com.example.SunBase.exception.CustomerNotFound;
import com.example.SunBase.model.Customer;

import java.util.List;

public interface CustomerService {

     public List<CustomerResponseDto> getAllCustomer();

     CustomerResponseDto addCustomer(Customer customer) throws CustomerAlreadyExist;

     CustomerResponseDto getById(int id) throws CustomerNotFound;

     String delete(String email) throws CustomerNotFound;

    CustomerResponseDto updateCustomer(CustomerRequestDto customerRequestDto, String email) throws CustomerNotFound;
    public List<CustomerResponseDto> searchBy(String attribute, String value);

//    public String upadateSync(TestRequestDto testRequestDto);
}

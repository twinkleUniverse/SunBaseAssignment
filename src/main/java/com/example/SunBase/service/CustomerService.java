package com.example.SunBase.service;

import com.example.SunBase.dtos.CustomerRequestDto;
import com.example.SunBase.dtos.CustomerResponseDto;
import com.example.SunBase.exception.CustomerAlreadyExist;
import com.example.SunBase.exception.CustomerNotFound;
import com.example.SunBase.model.Customer;

import java.util.List;

public interface CustomerService {

     public List<CustomerResponseDto> getAllCustomer();

     Customer addCustomer(Customer customer) throws CustomerAlreadyExist;

     CustomerResponseDto getById(int id) throws CustomerNotFound;

     String delete(int id) throws CustomerNotFound;

    CustomerResponseDto updateCustomer(CustomerRequestDto customerRequestDto, int id) throws CustomerNotFound;
    public List<CustomerResponseDto> searchBy(String attribute, String value);
}
